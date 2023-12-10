package com.example.demo.services.impls;

import com.example.demo.security.SecurityUserDetails;
import com.example.demo.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    String SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    int TTl = 1000 * 60 * 24;

    @Override
    public String extractPhoneNumber(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);

        return claimsResolver.apply(claims);
    }

    @Override
    public Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(jwt).getPayload();
    };

    @Override
    public SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, SecurityUserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.customUser.getPhoneNumber())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TTl))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    @Override
    public Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    @Override
    public boolean IsTokenValid(String jwt, SecurityUserDetails userDetails) {
        final String phoneNumber = extractPhoneNumber(jwt);
        return (phoneNumber.equals(userDetails.getPhoneNumber())) && !isTokenExpired(jwt);
    }
}

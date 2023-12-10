package com.example.demo.services;

import com.example.demo.security.SecurityUserDetails;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


public interface JwtService {
    public String extractPhoneNumber(String jwt);

    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver);

    public Claims extractAllClaims(String jwt);

    public SecretKey getSignInKey();

    public String generateToken(Map<String, Object> extraClaims, SecurityUserDetails userDetails);

    public boolean isTokenExpired(String jwt);

    public Date extractExpiration(String jwt);

    public boolean IsTokenValid(String jwt, SecurityUserDetails userDetails);
}

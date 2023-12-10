package com.example.demo.security;

import com.example.demo.models.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;


@RequiredArgsConstructor
public class SecurityUserDetails implements UserDetails {
    public final CustomUser customUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    public String getPhoneNumber() {
        return customUser.getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return customUser.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return customUser.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return customUser.isActive();
    }

    @Override
    public boolean isEnabled() {
        return customUser.isActive();
    }
}

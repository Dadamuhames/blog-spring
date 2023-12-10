package com.example.demo.services.impls;

import com.example.demo.models.CustomUser;
import com.example.demo.repo.CustomUserRepository;
import com.example.demo.security.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    public CustomUserRepository customUserRepository;
    @Override
    public SecurityUserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        CustomUser user = customUserRepository.findByPhoneNumberAndIsActive(phoneNumber, true).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        return new SecurityUserDetails(user);
    }
}

package com.example.demo.services.impls;

import com.example.demo.models.Admin;
import com.example.demo.repo.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AdminDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminUserRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        User.UserBuilder builder = User.withUsername(admin.getUsername());
        builder.password(admin.getPassword());

        return builder.build();
    }
}

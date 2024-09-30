package com.example.demo.security;


import com.example.demo.services.impls.AdminDetailsServiceImpl;
import com.example.demo.services.impls.CustomUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Configuration
    @Order(1)
    public static class AdminConfigurationAdapter {
        @Autowired
        public AdminDetailsServiceImpl adminDetailsService;
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(passwordEncoder());
            provider.setUserDetailsService(adminDetailsService);
            return provider;
        }

        @Bean
        public SecurityFilterChain filterChainAdmin(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth
                            .requestMatchers("/*", "/admin/assets/**", "/files/**", "/admin/assets/**", "/css/**", "/js/**", "/admin/login", "/api/**").permitAll()
                            .requestMatchers("/admin/**").authenticated()
            )
            .formLogin(formLogin ->
                    formLogin.loginPage("/admin/login")
                            .loginProcessingUrl("/admin/login")
                            .defaultSuccessUrl("/admin/posts", true)
                            .failureUrl("/admin/login?error=loginError")
            )
            .logout((logout) -> logout
                    .logoutSuccessUrl("/admin/login")
                    .logoutUrl("/admin/logout"))
            .httpBasic(AbstractHttpConfigurer::disable)
                    .csrf(csrf ->
                            csrf.ignoringRequestMatchers("/api/**"));

            return http.build();
        }
    }


    @Configuration
    @Order(2)
    public static class ApiConfigurationAdapter {
        @Autowired
        public CustomUserDetailServiceImpl customUserDetailService;

    }
}

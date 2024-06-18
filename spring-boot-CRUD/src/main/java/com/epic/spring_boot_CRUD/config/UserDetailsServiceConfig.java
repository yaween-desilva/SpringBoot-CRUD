package com.epic.spring_boot_CRUD.config;

import com.epic.spring_boot_CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDetailsServiceConfig {

    @Autowired
    private UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            com.epic.spring_boot_CRUD.entity.User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found.");
            }
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities("ROLE_USER")
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


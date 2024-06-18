package com.epic.spring_boot_CRUD.controller;

import com.epic.spring_boot_CRUD.entity.User;
import com.epic.spring_boot_CRUD.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

//    @PostMapping("/token")
//    public String token(Authentication authentication) {
//        return tokenService.generateToken(authentication);
//    }

    @PostMapping("/token")
    public String token(@RequestBody User user) {
        // Create an Authentication object
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Generate the token
        return tokenService.generateToken(authentication);
    }

}

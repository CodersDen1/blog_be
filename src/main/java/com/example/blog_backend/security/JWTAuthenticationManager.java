package com.example.blog_backend.security;

import com.example.blog_backend.users.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationManager implements AuthenticationManager {
    private final JWTService jwtService;
    private final UserService userService;

    public JWTAuthenticationManager(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authentication instanceof JWTAuthentication jwtAuthentication) {

            var jwt = jwtAuthentication.getCredentials();
            var userId = jwtService.retrieveUserId(jwt);

            jwtAuthentication.userEntity = userService.getUser(userId);
            jwtAuthentication.setAuthenticated(true);

            return jwtAuthentication;
        }

        throw new IllegalAccessError("Cannot authenticate with non-JWT authentication");
    }
}
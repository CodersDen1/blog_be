package com.example.blog_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.security.BasicPermission;
import java.security.Security;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequest) -> authorizeHttpRequest
                        .requestMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET , "/*" , "/h2-console/**").permitAll().anyRequest().authenticated()
                ).formLogin(Customizer.withDefaults());


        return http.build();

    }

}

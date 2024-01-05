package com.example.blog_backend.security;

import com.example.blog_backend.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final JWTService jwtService;
    private  final UserService userService;


    @Autowired
    public AppSecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter, UserService userService, JWTService jwtService, UserService userService1) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtService = jwtService;
        this.userService = userService1;
    }






    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequest) -> authorizeHttpRequest
                        .requestMatchers(HttpMethod.POST, "/users", "/users/login" , "/articles").permitAll()
                        .requestMatchers(HttpMethod.GET,   "/articles","/articles/{id}").permitAll().anyRequest().authenticated()
                ).formLogin(Customizer.withDefaults());
        http.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);

        return http.build();
    }
}

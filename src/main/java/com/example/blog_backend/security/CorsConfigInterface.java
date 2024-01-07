package com.example.blog_backend.security;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public interface CorsConfigInterface extends WebMvcConfigurer {
    void addCorsMapping(CorsRegistry registry);
}

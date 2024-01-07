package com.example.blog_backend.users.dtos;

import lombok.Data;
import lombok.Getter;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private  String email;
    private String bio;
    private  byte[] image;
    private String token;
}

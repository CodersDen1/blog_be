package com.example.blog_backend.comments.dtos;

import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private String title;
    private String body;
}

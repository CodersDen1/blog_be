package com.example.blog_backend.comments.dtos;

import com.example.blog_backend.articles.ArticleEntity;
import com.example.blog_backend.users.UserEntity;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateCommentRequest {

    @NonNull private String title;
    @NonNull private String body;

    private ArticleEntity article;
    private UserEntity author;


}

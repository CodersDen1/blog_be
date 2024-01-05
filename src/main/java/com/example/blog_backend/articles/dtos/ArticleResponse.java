package com.example.blog_backend.articles.dtos;

import com.example.blog_backend.users.UserEntity;
import lombok.Data;

@Data
public class ArticleResponse {
    String id;
    String title;
    String body;
    String subtitle;
    String slug;

}

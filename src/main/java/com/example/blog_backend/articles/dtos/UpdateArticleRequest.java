package com.example.blog_backend.articles.dtos;

import com.example.blog_backend.users.UserEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Data
@Setter(AccessLevel.NONE)
public class UpdateArticleRequest {

    @Nullable
    private String title;

    @Nullable
    private String body;

    @Nullable
    private String subtitle;

    private UserEntity authorId;


}

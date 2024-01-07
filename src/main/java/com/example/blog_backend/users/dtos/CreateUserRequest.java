package com.example.blog_backend.users.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Data
@Setter(AccessLevel.NONE)
public class CreateUserRequest {
    @NonNull private String username;
    @NonNull private String password;
    @NonNull private String email;
    @Nullable byte [] image;
}

package com.example.blog_backend.comments;

import com.example.blog_backend.articles.ArticleEntity;
import com.example.blog_backend.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import java.util.Date;

@Entity(name = "comments")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Nullable
    private String title;

    @NonNull
    private String body;

    @CreatedDate
    private Date createdAt;


    @ManyToOne
    @JoinColumn(name = "articleId", nullable = false)
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "authorid" , nullable = false)
    private UserEntity user;
}

package com.example.blog_backend.articles;

import com.example.blog_backend.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import java.util.Date;

@Entity(name = "articles")
@Getter
@Setter
@Builder
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@AllArgsConstructor
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @NonNull
    private String title;
    @NonNull
    private String body;
    @Nullable
    private String subtitle;
    @NonNull
    @Column(unique = true)
    private String slug;

    @CreatedDate
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "authorId",nullable = false)
    private UserEntity author;

}

package com.example.blog_backend.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity , Long> {

    ArrayList<CommentEntity> findByArticleId(Long articleId);

}

package com.example.blog_backend.comments;

import com.example.blog_backend.articles.ArticleRepository;
import com.example.blog_backend.articles.ArticleService;
import com.example.blog_backend.comments.dtos.CreateCommentRequest;
import com.example.blog_backend.users.UserRepository;
import com.example.blog_backend.users.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private  final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, ArticleRepository articleRepository){
        this.commentRepository=commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }


    public ArrayList<CommentEntity> getCommentByArticleId(Long articleId){
        return  commentRepository.findByArticleId(articleId);
    }

    public CommentEntity createAComment(CreateCommentRequest request , Long articleId , Long authorId){
        var author = userRepository.findById(authorId).orElseThrow(()-> new UserService.UserNotFoundException(authorId));
        var article = articleRepository.findById(articleId).orElseThrow(()-> new ArticleService.ArticleNotFoundException(articleId));

        return commentRepository.save(
                CommentEntity.builder()
                        .title(request.getTitle())
                        .body(request.getBody())
                        .author(author)
                        .article(article)
                        .build()

        );
    }

}

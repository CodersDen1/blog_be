package com.example.blog_backend.articles;

import com.example.blog_backend.articles.dtos.ArticleResponse;
import com.example.blog_backend.articles.dtos.CreateArticleRequest;
import com.example.blog_backend.articles.dtos.UpdateArticleRequest;
import com.example.blog_backend.users.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.blog_backend.common.dtos.ErrorResponse;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final ModelMapper modelMapper;


    public ArticleController(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    ResponseEntity<ArrayList<ArticleEntity>> getAllArticle(){
        var articles = articleService.getAllArticle();
        return ResponseEntity.ok((ArrayList<ArticleEntity>) articles);
    }

    @GetMapping("/{id}")
    ResponseEntity<Optional<ArticleEntity>> getArticleById(@PathVariable("id") Long id ){

        var article = articleService.getArticleById(id);

        return ResponseEntity.ok(article);
    }

    @GetMapping("/{slug}")
    ResponseEntity<ArticleResponse> getArticleBySlug(@PathVariable("slug") String slug){
        var article =articleService.getArticleBySlug(slug);
        ArticleResponse response = modelMapper.map(article,ArticleResponse.class);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    ResponseEntity<ArticleResponse> updateArticle(@PathVariable("id") Long articleId , @RequestBody UpdateArticleRequest req){
        var article = articleService.updateArticle(req,articleId);

        ArticleResponse response = modelMapper.map(article , ArticleResponse.class);
         return ResponseEntity.ok(response);

    }

    @PostMapping("")
    ResponseEntity<ArticleResponse> createArticle(@AuthenticationPrincipal UserEntity user , @RequestBody CreateArticleRequest req){

        var newArticle = articleService.createNewArticle(req, user.getId());
        var articleResposne = modelMapper.map(newArticle, ArticleResponse.class);
        return ResponseEntity.ok(articleResposne);

    }


    @ExceptionHandler({
            ArticleService.ArticleNotFoundException.class
    })
    ResponseEntity<ErrorResponse> handleArtilceNotFoundException(Exception e){

        String message;
        HttpStatus status;

        if (e instanceof ArticleService.ArticleNotFoundException){
         message = e.getMessage();
         status = HttpStatus.NOT_FOUND;

        }
        else{
            message="something went worng";
            status=HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse response = ErrorResponse.builder().message(message).build();

        return ResponseEntity.status(status).body(response);
    }

}

package com.example.blog_backend.articles;

import com.example.blog_backend.articles.dtos.CreateArticleRequest;
import com.example.blog_backend.articles.dtos.UpdateArticleRequest;
import com.example.blog_backend.users.UserRepository;
import com.example.blog_backend.users.UserService;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    public ArticleService(ArticleRepository articleRepository , UserRepository userRepository){

        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public Iterable<ArticleEntity> getAllArticle(){
        return articleRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug){
        var article = articleRepository.findBySlug(slug);
        if(article==null){
         throw new ArticleNotFoundException(slug);
        }

        return article;
    }

    public ArticleEntity createNewArticle(CreateArticleRequest req , Long authorId){

        var author = userRepository.findById(authorId).orElseThrow(()->new UserService.UserNotFoundException(authorId));
        return articleRepository.save(
                ArticleEntity.builder()
                        .title(req.getTitle())
                        .slug(req.getTitle().toLowerCase().replaceAll("\\s+","-"))
                        .body(req.getBody())
                        .subtitle(req.getSubtitle())
                        .author(author)
                        .build()
        );
    }

    public ArticleEntity updateArticle( UpdateArticleRequest req , Long articleId){
        var article= articleRepository.findById(articleId).orElseThrow(()->new ArticleNotFoundException(articleId));
        if(req.getTitle()!=null){
         article.setTitle(req.getTitle());
        }
        if(req.getBody()!=null){
            article.setTitle(req.getBody());
        }
        if(req.getSubtitle()!=null){
            article.setTitle(req.getSubtitle());
        }

        return articleRepository.save(article);
    }


    public static class ArticleNotFoundException extends IllegalArgumentException{
        public ArticleNotFoundException(String slug){
            super("Article with the slug:"+slug+" \t not found");
        }
        public ArticleNotFoundException(Long articleId){
            super("Article with articleId : "+articleId+"not found");
        }

    }

}

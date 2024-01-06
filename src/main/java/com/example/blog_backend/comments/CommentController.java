package com.example.blog_backend.comments;


import com.example.blog_backend.comments.dtos.CommentResponse;
import com.example.blog_backend.comments.dtos.CreateCommentRequest;
import com.example.blog_backend.users.UserEntity;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles/{articleId}/comments")
public class CommentController {

   private final CommentService commentService;
   private final ModelMapper modelMapper;
    public CommentController(CommentService commentService, ModelMapper modelMapper) {

        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("")
    ResponseEntity<CommentResponse> createComment(@AuthenticationPrincipal UserEntity user , @PathVariable("articleId") Long articleId , @RequestBody CreateCommentRequest request){
        var newComment = commentService.createAComment(request , articleId , user.getId() );
        var commentResponse = modelMapper.map(newComment , CommentResponse.class);

        return ResponseEntity.ok(commentResponse);
    }


}

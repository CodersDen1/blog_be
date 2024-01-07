package com.example.blog_backend.users;

import com.example.blog_backend.articles.dtos.ArticleResponse;
import com.example.blog_backend.common.dtos.ErrorResponse;
import com.example.blog_backend.security.JWTService;
import com.example.blog_backend.users.dtos.CreateUserRequest;
import com.example.blog_backend.users.dtos.LoginUserRequest;
import com.example.blog_backend.users.dtos.UserResponse;
import com.example.blog_backend.users.dtos.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final  UserService userService;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    public UserController(UserService userService , ModelMapper modelMapper , JWTService jwtService){

        this.userService=userService;
        this.modelMapper=modelMapper;
        this.jwtService=jwtService;
    }
    @PostMapping("")
    ResponseEntity<UserResponse> signUpUser(@RequestBody CreateUserRequest request){
        var savedUser = userService.createUser(request);
        URI savedUserUri = URI.create("/users/"+savedUser.getId());

        var savedUserResponse = modelMapper.map(savedUser,UserResponse.class);

        savedUserResponse.setToken(
                jwtService.createJwt(savedUser.getId())
        );

        return ResponseEntity.created(savedUserUri)
                .body(savedUserResponse);
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest request){
        UserEntity savedUser = userService.loginUser(request.getUsername(), request.getPassword());
        var userResponse = modelMapper.map(savedUser,UserResponse.class);
        userResponse.setToken(
                jwtService.createJwt(savedUser.getId())
        );

        return ResponseEntity.ok(userResponse);
    }


    @GetMapping("")
    public String hello(){
        return "hello world api";
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long userId){
        var user = userService.getUser(userId);
        UserResponse response = modelMapper.map(user , UserResponse.class);
        return ResponseEntity.ok(response);
    }



    @ExceptionHandler({
            UserService.UserNotFoundException.class,
            UserService.InvalidCredentialsException.class
    })
    ResponseEntity<ErrorResponse>handleUserNotFoundException(Exception e){
        String message;
        HttpStatus status;

        if (e instanceof UserService.UserNotFoundException){
            message = e.getMessage();
            status = HttpStatus.NOT_FOUND;
        }
        else if(e instanceof UserService.InvalidCredentialsException){
            message = e.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        }
        else{
            message="something went worng";
            status=HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse response = ErrorResponse.builder()
                .message(message)
                .build();

        return ResponseEntity.status(status).body(response);
    }

}

package com.example.blog_backend.users;

import com.example.blog_backend.common.dtos.ErrorResponse;
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

    public UserController(UserService userService , ModelMapper modelMapper){

        this.userService=userService;
        this.modelMapper=modelMapper;
    }
    @PostMapping("")
    ResponseEntity<UserResponse> signUpUser(@RequestBody CreateUserRequest request){
        var savedUser = userService.createUser(request);
        URI savedUserUri = URI.create("/users/"+savedUser.getId());
        return ResponseEntity.created(savedUserUri).body(modelMapper.map(savedUser ,UserResponse.class));
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest request){
        UserEntity savedUser = userService.loginUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(modelMapper.map(savedUser,UserResponse.class));
    }






    @ExceptionHandler({UserService.UserNotFoundException.class})
    ResponseEntity<ErrorResponse>handleUserNotFoundException(Exception e){
        String message;
        HttpStatus status;

        if (e instanceof UserService.UserNotFoundException){
            message = e.getMessage();
            status = HttpStatus.NOT_FOUND;
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
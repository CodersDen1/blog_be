package com.example.blog_backend.users;

import com.example.blog_backend.users.dtos.CreateUserRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public UserEntity createUser(CreateUserRequest req){
        var newUser = UserEntity.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                //.password(req.getPassword()) todo : make it encrypted
                .build();

        return userRepository.save(newUser);
    }

    public UserEntity getUser(String username){

        return userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException(username));
    }
    public  UserEntity getUser(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
    }


    public UserEntity loginUser(String username, String password){
        var user = userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
        return user;
    }

    public static class UserNotFoundException extends IllegalArgumentException{
        public  UserNotFoundException(String username){
            super("Username" + username +" not found");
        }
        public  UserNotFoundException(Long userId){
            super("userId" + userId +" not found");
        }
    }
}

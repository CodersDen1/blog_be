package com.example.blog_backend.users;

import com.example.blog_backend.users.dtos.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,ModelMapper modelMapper,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.modelMapper=modelMapper;
        this.passwordEncoder=passwordEncoder;
    }

    public UserEntity createUser(CreateUserRequest req){
        UserEntity newUser = modelMapper.map(req , UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
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
        var passMatch = passwordEncoder.matches(password,user.getPassword());

        if(!passMatch) throw new InvalidCredentialsException();

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

    public static class InvalidCredentialsException extends IllegalArgumentException{
        public InvalidCredentialsException(){
            super("Invalid credential combination");
        }
    }
}

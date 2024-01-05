package com.example.blog_backend.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    //TODO : Move the key to separate property file
    private  static final String JWT_KEY = "asdafwgggabfdfsdaymnghdmnfaeytjfagdghawfsdgragafnbymnfvef5e4sfvsafd";

    private Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);

    public String createHJwt(Long userId){
       return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
                //.withExpiresAt(new Date())
                .sign(algorithm);
    }

    public Long retrieveUserId(String jwtString){
        var decodedJWT =JWT.decode(jwtString);
        var userId = Long.valueOf(decodedJWT.getSubject());
        return userId;
    }

}

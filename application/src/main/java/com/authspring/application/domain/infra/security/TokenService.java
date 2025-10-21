package com.authspring.application.domain.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.authspring.application.domain.entities.User;
import com.authspring.application.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Autowired
    UserRepository userRepository;

    @Value("${api.security.secret}")
    private String secret;

    public String generateToken (User user){
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("application-auth")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            return token;

        } catch (JWTCreationException e) {
            throw new RuntimeException("Error While authentication");
        }
    }

    public String validateToken(String token){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("application-auth")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-3 horas"));
    }
}

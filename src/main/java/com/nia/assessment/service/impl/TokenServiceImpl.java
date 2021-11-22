package com.nia.assessment.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nia.assessment.model.UserEntity;
import com.nia.assessment.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${token.secret}")
    private String secret;

    @Value("${token.access.ttl}")
    private Long ttlAccessToken;

    @Value("${token.refresh.ttl}")
    private Long ttlRefreshToken;

    private Algorithm algorithm;

    @Override
    public String getAccessToken(User user, String issuer) {
        algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (ttlAccessToken * 60L * 1000L)))
                .withIssuer(issuer)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withClaim("refresh", false)
                .sign(algorithm);
    }

    @Override
    public String getAccessToken(UserEntity userEntity, String issuer) {
        algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(userEntity.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (ttlAccessToken * 60L * 1000L)))
                .withIssuer(issuer)
                .withClaim("roles", List.of("ROLE_USER"))
                .withClaim("refresh", false)
                .sign(algorithm);

    }

    @Override
    public String getRefreshToken(User user, String issuer) {
        algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (ttlRefreshToken * 60L * 1000L)))
                .withIssuer(issuer)
                .withClaim("refresh", true)
                .sign(algorithm);
    }

    @Override
    public String getRefreshToken(UserEntity userEntity, String issuer) {
        algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(userEntity.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (ttlRefreshToken * 60L * 1000L)))
                .withIssuer(issuer)
                .withClaim("refresh", true)
                .sign(algorithm);
    }

    @Override
    public String getSecret() {
        return secret;
    }
}
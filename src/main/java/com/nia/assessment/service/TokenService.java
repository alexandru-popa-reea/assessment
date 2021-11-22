package com.nia.assessment.service;

import com.nia.assessment.model.UserEntity;
import org.springframework.security.core.userdetails.User;

public interface TokenService {
    String getAccessToken(User user, String issuer);
    String getAccessToken(UserEntity userEntity, String issuer);
    String getRefreshToken(User user, String issuer);
    String getRefreshToken(UserEntity userEntity, String issuer);
    String getSecret();
}

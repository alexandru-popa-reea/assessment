package com.nia.assessment.service;

import com.nia.assessment.model.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity getUser(String username);
    UserEntity saveUser(UserEntity userEntity);
    void deleteUser(Long id);
    List<UserEntity> getAllUsers();
}

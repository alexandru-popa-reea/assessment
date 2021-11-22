package com.nia.assessment.controller;

import com.nia.assessment.model.UserEntity;
import com.nia.assessment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }
}

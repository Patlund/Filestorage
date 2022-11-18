package com.example.filestorage.controllers;

import com.example.filestorage.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody RegisterUser registerUser){
        var user = userService.registerUser(registerUser.username,registerUser.password);
        return ResponseEntity.ok(user.getUsername() + " has been created!");

    }

    @Getter
    @Setter
    public static class RegisterUser{
        private String username;
        private String password;
    }

}

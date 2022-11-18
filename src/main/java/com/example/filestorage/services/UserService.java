package com.example.filestorage.services;

import com.example.filestorage.data.User;
import com.example.filestorage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String password){
        var user = new User(UUID.randomUUID().toString(),username,password);
        return userRepository.save(user);
    }

    public ResponseEntity deleteUser(String username){
        var user = userRepository.findByUsername(username);
        userRepository.delete(user);
        return ResponseEntity.ok("The user has been removed");
    }
}

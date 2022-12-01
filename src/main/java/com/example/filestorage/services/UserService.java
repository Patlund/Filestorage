package com.example.filestorage.services;

import com.example.filestorage.data.User;
import com.example.filestorage.exceptions.UserAlreadyExistsException;
import com.example.filestorage.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String password)
            throws UserAlreadyExistsException
    {
        var existing = userRepository.findByUsername(username);
        if (existing.isPresent()) {
            log.info("A user with the username: '" + username + "' already exists.");
            throw new UserAlreadyExistsException();
        }

        var user = new User(UUID.randomUUID().toString(),passwordEncoder.encode(password),username);
        log.info("Successfully registered user with username '" + user.getUsername() + "'.");
        return userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("A user with username '" + username + "' could not be found."));

        return user;
    }
}

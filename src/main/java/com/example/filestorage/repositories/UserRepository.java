package com.example.filestorage.repositories;

import com.example.filestorage.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findByUsername(String username);
}

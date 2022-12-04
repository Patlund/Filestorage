package com.example.filestorage.repositories;

import com.example.filestorage.data.File;
import com.example.filestorage.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File,String> {

}

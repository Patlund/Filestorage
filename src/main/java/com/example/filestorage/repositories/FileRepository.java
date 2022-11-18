package com.example.filestorage.repositories;

import com.example.filestorage.data.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File,String> {
}

package com.example.filestorage.services;

import com.example.filestorage.data.File;
import com.example.filestorage.data.User;
import com.example.filestorage.repositories.FileRepository;
import com.example.filestorage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Autowired
    public FileService(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    public File store(MultipartFile file, User user) throws IOException {
        var fileToStore = new File(UUID.randomUUID().toString(), file.getOriginalFilename(), file.getContentType(), user, file.getBytes());
        return fileRepository.save(fileToStore);

    }

    public File getFileById(String id){
        Optional<File> optionalFile = fileRepository.findById(id);
        if(optionalFile.isPresent()){
            return optionalFile.get();
        }
        return null;
    }

    public List<File> getAllFiles(){
        return fileRepository.findAll();
    }

    public File deleteFile(String id){
        var fileToDelete = fileRepository.findById(id);
        if(fileToDelete.isPresent()){
            fileRepository.delete(fileToDelete.get());
        }
        return null;
    }


}

package com.example.filestorage.services;

import com.example.filestorage.data.File;
import com.example.filestorage.repositories.FileRepository;
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

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File store(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        var fileToStore = new File(UUID.randomUUID().toString(),filename,file.getContentType(),file.getBytes());
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


}

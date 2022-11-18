package com.example.filestorage.controllers;

import com.example.filestorage.data.File;
import com.example.filestorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("file")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public File uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.store(file);
    }
    @GetMapping("/{id}")
    public File getFileById(@PathVariable String id){
        return fileService.getFileById(id);
    }

    @GetMapping("/list")
    public List<File> getFileList(){
        return fileService.getAllFiles();
    }


}

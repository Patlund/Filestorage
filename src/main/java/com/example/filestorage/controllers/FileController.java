package com.example.filestorage.controllers;

import com.example.filestorage.data.File;
import com.example.filestorage.data.User;
import com.example.filestorage.services.FileService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @PostMapping("upload")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal User user) throws IOException {
        File attachment = fileService.store(file, user);
        var downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(attachment.getId())
                .toUriString();
        var response = new DownloadResponse(file.getName(),downloadURL,file.getContentType(),file.getSize());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, @AuthenticationPrincipal User user) throws Exception {
        var attachment = fileService.getFileById(fileId);
        if(attachment != null && attachment.getUser() == user) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(attachment.getType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + attachment.getName()
                                    + "\"")
                    .body(new ByteArrayResource(attachment.getData()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<File> deleteFile(@PathVariable String fileId){
        var fileToDelete = fileService.getFileById(fileId);
        fileService.deleteFile(fileId);
        return ResponseEntity.ok(fileToDelete);
    }

    @GetMapping("/get/{id}")
    public File getFileById(@PathVariable String id){
        return fileService.getFileById(id);
    }

    @GetMapping("/list")
    public List<File> getFileList(){
        return fileService.getAllFiles();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class DownloadResponse{

        private String filename;
        private String downloadURL;
        private String fileType;
        private long fileSize;
    }
}



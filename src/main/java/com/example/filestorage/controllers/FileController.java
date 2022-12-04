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
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        var response = new DownloadResponse(file.getName(),downloadURL,file.getContentType(),attachment.getUser().getUsername(),file.getSize());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, @AuthenticationPrincipal User user) throws Exception {
        var attachment = fileService.getFileById(fileId);
        if(attachment != null &&
                user.getUserId().equals(attachment.getUser().getUserId()) &&
                user.getUsername().equals(attachment.getUser().getUsername())) {
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
    public ResponseEntity<File> deleteFile(@PathVariable String fileId, @AuthenticationPrincipal User user){
        var fileToDelete = fileService.getFileById(fileId);
        if(fileToDelete != null &&
                user.getUserId().equals(fileToDelete.getUser().getUserId()) &&
                user.getUsername().equals(fileToDelete.getUser().getUsername())) {
            fileService.deleteFile(fileId);
            var response = new DeleteResponse(fileToDelete.getName(),fileToDelete.getType(),fileToDelete.getUser().getUsername());
            return ResponseEntity.ok(fileToDelete);
        }
        return ResponseEntity.notFound().build();
    }


    @Getter
    @Setter
    @AllArgsConstructor
    public static class DownloadResponse{
        private String filename,downloadURL,fileType, user;
        private long fileSize;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class DeleteResponse{
        private String filename,fileType,username;

    }
}



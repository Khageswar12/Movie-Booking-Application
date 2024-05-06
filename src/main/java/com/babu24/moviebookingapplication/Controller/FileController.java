package com.babu24.moviebookingapplication.Controller;

import com.babu24.moviebookingapplication.Service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Value("${project.poster}")
    private String path;

    @PostMapping("/uplode")
    public ResponseEntity<String> uploadFileHandler
            (@RequestPart MultipartFile file)throws IOException {

        String uloadedFileName=fileService.uploadFile(path,file);
        return ResponseEntity.ok("File uploaded :"+ uloadedFileName);
    }

    @GetMapping("/{fileName}")
    public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response )
            throws IOException {

       InputStream resourceFile= fileService.getResoureFile(path,fileName);
       response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resourceFile,response.getOutputStream());

    }
}

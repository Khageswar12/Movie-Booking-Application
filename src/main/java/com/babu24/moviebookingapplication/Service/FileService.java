package com.babu24.moviebookingapplication.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String uploadFile(String path, MultipartFile file)
            throws IOException;

    InputStream getResoureFile(String path,String FileName)
            throws FileNotFoundException;


}

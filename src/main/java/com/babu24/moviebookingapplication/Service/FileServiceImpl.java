package com.babu24.moviebookingapplication.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public InputStream getResoureFile(String path, String name) throws FileNotFoundException {
        return null;
    }
}

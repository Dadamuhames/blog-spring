package com.example.demo.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    public String getNewFileName(String fileName);

    public String getFileUploadDir();

    public String saveFile(MultipartFile file) throws IOException;

    public boolean deleteFile(String path);

    public void deleteFiles(String filesKey, HttpSession session);
}
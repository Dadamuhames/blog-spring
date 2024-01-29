package com.example.demo.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface FileGetService {
    public String getShowUrl(HttpServletRequest request, String filePath);

    public String getImage(HttpSession session, String fileKey);

    public List<String> getImages(HttpSession session, String fileKey);

    public String getCorrectFilePath(String path);

    public String getPhotoUrlShow(String photoUrl, HttpServletRequest request, int width, int height);
}

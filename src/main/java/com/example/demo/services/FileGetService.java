package com.example.demo.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface FileGetService {
    public String getShowUrl(HttpServletRequest request, String filePath);

    public String getImage(HttpSession session);

    public String getThumbFilePath(String filePath, String size);

    public String getThumbnailImage(String filePath, int width, int height);

    public String generateThumbnailImage(String filePath, int width, int height);

    public String getCorrectFilePath(String path);

    public String getPhotoUrlShow(String photoUrl, HttpServletRequest request, int width, int height);
}

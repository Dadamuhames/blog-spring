package com.example.demo.services;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface FileThumbnailService {
    String getThumbFilePath(String filePath, String size);

    String getThumbnailImage(String filePath, int width, int height);

    String generateThumbnailImage(String filePath, int width, int height);

    void saveThumbnailAsWebp(BufferedImage bufferedImage, String savePath) throws IOException;
}

package com.example.demo.services.impls;

import com.example.demo.services.FileThumbnailService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


@Service
@RequiredArgsConstructor
public class FileThumbnailServiceImpl implements FileThumbnailService {
    @Override
    public String getThumbFilePath(String filePath, String size) {
        String fileExt = "." + FilenameUtils.getExtension(filePath);
        String fileAbsolutePath = new File(filePath).getAbsolutePath().replace(fileExt, "");

        return String.format("%s.%s.jpeg", fileAbsolutePath, size);
    }

    @Override
    public String getThumbnailImage(String filePath, int width, int height) {
        String thumbFilePath = getThumbFilePath(filePath, String.format("%dx%d", width, height));

        boolean fileExists = new File(thumbFilePath).exists();

        if(!fileExists) {
            thumbFilePath = generateThumbnailImage(filePath, width, height);
        }

        return thumbFilePath == null ? "" : thumbFilePath;
    }

    @Override
    public String generateThumbnailImage(String filePath, int width, int height) {
        String thumbFilePath = getThumbFilePath(filePath, String.format("%dx%d", width, height));

        try {
            Thumbnails.of(filePath).size(width, height).outputFormat("jpeg").toFile(thumbFilePath);;
        } catch (Exception e) {
            thumbFilePath = null;

        }

        return thumbFilePath;
    }

    @Override
    public void saveThumbnailAsWebp(BufferedImage bufferedImage, String savePath) throws IOException {
        File outputFile = new File(savePath);

        System.out.println(bufferedImage);

        ImageIO.write(bufferedImage, "webp", outputFile);
    }
}

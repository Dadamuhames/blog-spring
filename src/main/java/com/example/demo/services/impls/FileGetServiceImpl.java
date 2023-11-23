package com.example.demo.services.impls;

import com.example.demo.services.FileGetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Array;
import java.util.List;


@Service
public class FileGetServiceImpl implements FileGetService {
    @Override
    public String getShowUrl(HttpServletRequest request, String filePath) {
        filePath = getCorrectFilePath(filePath);

        if(filePath != null) {
            String domain = request.getRequestURL().toString().replace(request.getRequestURI(),"");
            filePath = domain + "/files/" + filePath;
        }

        return filePath;
    }

    @Override
    public String getImage(HttpSession session) {
        List<String> sessionImages = (List<String>) session.getAttribute("images");

        String image = null;

        if(sessionImages != null && !sessionImages.isEmpty()) {
            image = sessionImages.getFirst();
        }

        return image;
    }

    @Override
    public String getThumbFilePath(String filePath, String size) {
        String fileExt = "." + FilenameUtils.getExtension(filePath);
        String fileAbsolutePath = new File(filePath).getAbsolutePath().replace(fileExt, "");

        return String.format("%s.%s.png", fileAbsolutePath, size);
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
            Thumbnails.of(filePath).size(width, height).outputFormat("png").toFile(thumbFilePath);
        } catch (Exception e) {
            thumbFilePath = null;
        }

        return thumbFilePath;
    }


    @Override
    public String getCorrectFilePath(String path) {
        String[] pathSplit = path.split("/uploads/");

        return "uploads/" + (String) Array.get(pathSplit, pathSplit.length - 1);
    }

    @Override
    public String getPhotoUrlShow(String photoUrl, HttpServletRequest request, int width, int height) {
        if(photoUrl == null || photoUrl.isEmpty()) { return "/admin/src/img/default.png"; }

        String fileThumbUrl = getThumbnailImage(photoUrl, width, height);

        if(fileThumbUrl == null || fileThumbUrl.isEmpty()) {
            fileThumbUrl = "/admin/src/img/default.png";
        }

        return getShowUrl(request, fileThumbUrl);
    }
}
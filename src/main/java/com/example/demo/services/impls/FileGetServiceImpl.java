package com.example.demo.services.impls;

import com.example.demo.services.FileGetService;
import com.example.demo.services.FileThumbnailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileGetServiceImpl implements FileGetService {
    private final FileThumbnailService fileThumbnailService;

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
    public String getImage(HttpSession session, String fileKey) {
        List<String> sessionImages = (List<String>) session.getAttribute(fileKey);

        String image = null;

        if(sessionImages != null && !sessionImages.isEmpty()) {
            image = sessionImages.getFirst();
        }

        return image;
    }

    @Override
    public List<String> getImages(HttpSession session, String fileKey) {
        List<String> sessionImages = (List<String>) session.getAttribute(fileKey);

        if(sessionImages == null) {
            return new ArrayList<>();
        }

        return sessionImages;
    }

    @Override
    public String getCorrectFilePath(String path) {
        String[] pathSplit = path.split("uploads/");

        return "uploads/" + (String) Array.get(pathSplit, pathSplit.length - 1);
    }

    // this method has usage, but in templates (don't remove it!!!)
    @Override
    public String getPhotoUrlShow(String photoUrl, HttpServletRequest request, int width, int height) {
        if(photoUrl == null || photoUrl.isEmpty()) { return "/admin/src/img/default.png"; }

        String fileThumbUrl = fileThumbnailService.getThumbnailImage(photoUrl, width, height);

        if(fileThumbUrl == null || fileThumbUrl.isEmpty()) {
            fileThumbUrl = "/admin/src/img/default.png";
        }

        return getShowUrl(request, fileThumbUrl);
    }
}

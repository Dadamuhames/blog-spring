package com.example.demo.controllers;



import com.example.demo.dto.PostDto;
import com.example.demo.services.FileGetService;
import com.example.demo.services.FileUploadService;
import com.example.demo.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files/")
public class FileUploadController {
    private final FileUploadService fileUploadService;
    private final FileGetService fileGetService;

   // upload file
    @PostMapping("/upload")
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request, HttpSession session) {
       Map<String, String> result = new HashMap<>();

       if(!file.isEmpty()) {
           try {
               String fileSavedPath = fileUploadService.saveFile(file);

               String filePath = fileGetService.getCorrectFilePath(fileSavedPath);

               result.put("filePath", filePath);
               result.put("deleteUrl", fileSavedPath);
               result.put("showUrl", fileGetService.getShowUrl(request, filePath));


               List<String> sessionImages = (List<String>) session.getAttribute("images");

               if(sessionImages == null) {
                   sessionImages = new ArrayList<>();
                   session.setAttribute("images", sessionImages);
               }

               sessionImages.add(filePath);

           } catch (Exception e) {
               response.setStatus(HttpServletResponse.SC_FORBIDDEN);
               result.put("error", e.getMessage());
           }

       } else {
           response.setStatus(HttpServletResponse.SC_FORBIDDEN);
           result.put("error", "file cannot be null");
       }

       return result;
   }


   // delete file by path
   @PostMapping("/delete")
    public Map<String, Object> deleteFile(@RequestParam("filePath") String filePath, HttpServletResponse response, HttpSession session) {
       Map<String, Object> result = new HashMap<>();
       Map<String, Object> errors = new HashMap<>();

       if(!filePath.isEmpty()) {
            try {
                Boolean deleted = fileUploadService.deleteFile(filePath);
                result.put("deleted", deleted);

                List<String> sessionImages = (List<String>) session.getAttribute("images");

                if(sessionImages != null) {
                    String fileDeletePath = fileGetService.getCorrectFilePath(filePath);
                    sessionImages.remove(fileDeletePath);
                }

            } catch (Exception e) {
                errors.put("error", e.getMessage());
            }

       } else {
           errors.put("error", "filePath cannot be null");
       }

       if(!errors.isEmpty()) {
           response.setStatus(HttpServletResponse.SC_FORBIDDEN);
           return errors;
       }

       return result;
   }
}

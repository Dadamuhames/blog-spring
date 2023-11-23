package com.example.demo.services.impls;

import com.example.demo.services.FileUploadService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.sql.Timestamp;
import java.util.List;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    ServletContext context;

    @Override
    public String getNewFileName(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return String.format("%d.%s", timestamp.getTime(), extension);
    }

    @Override
    public String getFileUploadDir() {
        String dirPath = null;
        LocalDate currentDate = LocalDate.now();

        dirPath = "uploads" + String.format("/%s/%s/%s/", currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth());

        return dirPath;
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        String savedFilePath = null;

        try {
            byte[] fileBytes = file.getBytes();

            String dirPath = getFileUploadDir();
            String fileName = getNewFileName(file.getOriginalFilename());

            File dir = new File(dirPath);

            if (!dir.exists())
                dir.mkdirs();

            File savedFile = new File(dir.getAbsolutePath() + File.separator + fileName);

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(savedFile));

            stream.write(fileBytes);
            stream.close();

            savedFilePath = savedFile.getPath();

        } catch(Exception e) {
            throw e;
        }

        return savedFilePath;
    }

    @Override
    public boolean deleteFile(String path) {
        File file = new File(path);

        return file.delete();
    }
}

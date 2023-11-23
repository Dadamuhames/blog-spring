package com.example.demo.dto;

import com.example.demo.models.Category;
import com.example.demo.services.FileGetService;
import com.example.demo.services.FileUploadService;
import com.example.demo.services.impls.FileGetServiceImpl;
import com.example.demo.services.impls.FileUploadServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.internal.log.SubSystemLogging;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private int index;
    private Long id;

    @NotEmpty(message = "Title cannot be blank")
    private String title;

    @NotEmpty(message = "Title cannot be blank")
    private String subtitle;

    private String text;

    private String photoUrl;

    private boolean active;
    private boolean top;
    private LocalDateTime createdAt;

    @NotNull(message = "Category cannot be blank")
    private Long category_id;

    private Category category;
}
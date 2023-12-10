package com.example.demo.controllers;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.PostDto;
import com.example.demo.services.CategoryService;
import com.example.demo.services.OtpService;
import com.example.demo.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BlogRestController {
    public final PostService postService;
    public final CategoryService categoryService;
    public final OtpService otpService;

    @GetMapping("/posts")
    public Page<PostDto> postsList(@RequestParam String key, @RequestParam(defaultValue = "false") boolean set) {
        Pageable pageable = PageRequest.of(0, 2);

        if(set) {
            otpService.storeOtp(key);
        }

        String otp = otpService.getOtp(key);

        System.out.println(otp + " !!!!!");

        return postService.findAllByTitleContainingOrderByIdDesc("", pageable);
    }


    @GetMapping("/categories")
    public Page<CategoryDto> categoryList() {
        Pageable pageable = PageRequest.of(0, 2);

        return categoryService.findAllByTitleContainingOrderByIdDesc("", pageable);
    }

}

package com.example.demo.controllers;


import com.example.demo.dto.PostDto;
import com.example.demo.models.Post;
import com.example.demo.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {
    public BlogController(PostService postService) {
        this.postService = postService;
    }

    private final PostService postService;

    @GetMapping("/blog")
    public String blogList(Model model) {
        List<PostDto> posts = postService.findByActive();

        model.addAttribute("posts", posts);

        return "main/blog";
    }

    @GetMapping("/blog/create")
    public  String blogCreate(Model model) {
        Post post = new Post();

        model.addAttribute("post", post);

        return "blog_create";
    }
}

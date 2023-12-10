package com.example.demo.controllers;


import com.example.demo.dto.PostDto;
import com.example.demo.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private final PostService postService;

    public MainController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);

        List<PostDto> postsTop = postService.findByActiveAndTop(true, true);
        List<PostDto> postsNotTop = postService.findByActiveAndTop(true, false);

        model.addAttribute("postsTop", postsTop);
        model.addAttribute("postsOther", postsNotTop);

        return "main/main";
    }
}
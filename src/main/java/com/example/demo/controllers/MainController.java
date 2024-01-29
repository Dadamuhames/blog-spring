package com.example.demo.controllers;


import com.example.demo.dto.EventDto;
import com.example.demo.dto.PostDto;
import com.example.demo.mappers.ModelLocaleMapper;
import com.example.demo.services.EventService;
import com.example.demo.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final PostService postService;
    private final EventService eventService;
    private final ModelLocaleMapper modelLocaleMapper;

    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);

        List<PostDto> postsTop = postService.findByActiveAndTop(true, true);
        List<PostDto> postsNotTop = postService.findByActiveAndTop(true, false);

        model.addAttribute("postsTop", postsTop);
        model.addAttribute("postsOther", postsNotTop);

        return "main/main";
    }


    @GetMapping("/test")
    public ResponseEntity<?> test(HttpServletRequest request) {

        EventDto eventDto = eventService.findById(2);

        Map<Object, Object> map = modelLocaleMapper.map(eventDto, request);

        return ResponseEntity.ok(map);
    }

}
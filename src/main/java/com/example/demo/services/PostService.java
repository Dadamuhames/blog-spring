package com.example.demo.services;

import com.example.demo.dto.PostDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface PostService {
    PostDto findById(long id);

    List<PostDto> findByActive();

    List<PostDto> findByActiveAndTop(boolean active, boolean top);

    Page<PostDto> findAllByTitleContainingOrderByIdDesc(String title, Pageable pageable);

    void savePost(PostDto postDto, HttpSession session);

    void deletePost(long id);

    void deletePostImage(long id);
}

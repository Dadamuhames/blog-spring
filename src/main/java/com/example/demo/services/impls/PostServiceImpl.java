package com.example.demo.services.impls;

import com.example.demo.dto.PostDto;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Category;
import com.example.demo.models.Post;
import com.example.demo.repo.CategoryRepository;
import com.example.demo.repo.PostRepository;
import com.example.demo.services.FileGetService;
import com.example.demo.services.FileUploadService;
import com.example.demo.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    public final PostRepository postRepository;

    public final CategoryRepository categoryRepository;

    public final FileUploadService fileUploadService;

    public final FileGetService fileGetService;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public PostDto findById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> findByActive() {
        List<Post> posts = postRepository.findByActive(true);

        return posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findByActiveAndTop(boolean active, boolean top) {
        List<Post> posts = postRepository.findByActiveAndTop(active, top);

        return posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<PostDto> findAllByTitleContainingOrderByIdDesc(String title, Pageable pageable) {
        Page<Post> posts = postRepository.findAllByTitleContainingOrderByIdDesc(title, pageable);

        AtomicInteger index = new AtomicInteger();

        return posts.map((post) -> {
            int ind = index.getAndIncrement();
            return toDtoWithIndex(post, ind + 1);
        });
    }

    @Override
    @Transactional
    public void savePost(PostDto postDto, HttpSession session) {
        Post post = modelMapper.map(postDto, Post.class);

        long category_id = postDto.getCategory_id();

        Category category = categoryRepository.findById(category_id).orElse(null);

        String image = fileGetService.getImage(session);

        post.setCategory(category);

        if (image != null && !image.isEmpty()) {
            post.setPhotoUrl(image);
        } else if(post.getId() != null) {
            Post old_post = postRepository.findById(post.getId()).orElse(null);

            assert old_post != null;
            post.setPhotoUrl(old_post.getPhotoUrl());
        }

        postRepository.save(post);
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public void deletePostImage(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        String file = post.getPhotoUrl();

        post.setPhotoUrl(null);
        fileUploadService.deleteFile(file);

        postRepository.save(post);
    }


    private PostDto toDtoWithIndex(Post post, int index) {
        return PostDto.builder()
                .index(index)
                .id(post.getId())
                .title(post.getTitle())
                .subtitle(post.getSubtitle())
                .photoUrl(post.getPhotoUrl())
                .active(post.isActive())
                .createdAt(post.getCreatedAt())
                .category(post.getCategory())
                .build();
    }
}

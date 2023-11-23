package com.example.demo.controllers.admin;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.PostDto;
import com.example.demo.models.Category;
import com.example.demo.models.Post;
import com.example.demo.services.CategoryService;
import com.example.demo.services.FileGetService;
import com.example.demo.services.FileUploadService;
import com.example.demo.utils.Paginate;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import com.example.demo.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/posts")
public class PostAdminController {
    private final PostService postService;

    private final CategoryService categoryService;

    private final FileUploadService fileUploadService;

    private final FileGetService fileGetService;


    // list show
    @GetMapping({"", "/"})
    public String postsList(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "20") int pageSize,
                            @RequestParam(defaultValue = "") String q) {

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<PostDto> posts = postService.findAllByTitleContainingOrderByIdDesc(q, pageable);

        int totalPages = posts.getTotalPages();

        List<Integer> pagination = Paginate.get_pagination(totalPages, page);

        model.addAttribute("posts", posts);
        model.addAttribute("pages", pagination);
        model.addAttribute("totalPages", totalPages);

        return "admin/blog/list";
    }

    // create form show
    @GetMapping("/create")
    public String getCreateForm(Model model, HttpSession session) {
        PostDto post = new PostDto();
        List<CategoryDto> categories = categoryService.findAll();

        model.addAttribute("post", post);
        model.addAttribute("categories", categories);

        List<String> emptyList = new ArrayList<>();

        session.setAttribute("images", emptyList);

        return "admin/blog/create";
    }

    // post save (create)
    @PostMapping("/create")
    public String postCreateForm(@Valid @ModelAttribute("post") PostDto postDto, BindingResult result, HttpSession session, Model model) {
        if(result.hasErrors()) {
            String image = fileGetService.getImage(session);

            model.addAttribute("requestImage", image);
            return "admin/blog/create";
        }

        postService.savePost(postDto, session);

        return "redirect:/admin/posts";
    }


    // update form show
    @GetMapping("/{id}/update")
    public String getUpdateForm(Model model, @PathVariable("id") long id, HttpSession session) {
        PostDto postDto = postService.findById(id);
        List<CategoryDto> categories = categoryService.findAll();

        model.addAttribute("post", postDto);
        model.addAttribute("categories", categories);

        List<String> emptyList = new ArrayList<>();

        session.setAttribute("images", emptyList);

        return "admin/blog/update";
    }


    // update form send
    @PostMapping("/{id}/update")
    public  String postUpdateForm(@Valid @ModelAttribute("post") PostDto postDto,
                                  @PathVariable("id") long id,
                                  BindingResult result,
                                  HttpSession session,
                                  Model model) {

        postDto.setId(id);

        if(result.hasErrors()) {
            String image = fileGetService.getImage(session);

            model.addAttribute("requestImage", image);

            return "admin/blog/update";
        }

        postService.savePost(postDto, session);

        return "redirect:/admin/posts";
    }


    // delete post
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") long id) {
        postService.deletePost(id);

        return "redirect:/admin/posts";
    }


    // delete image
    @PostMapping("/{id}/deleteImage")
    public String deletePostImage(@PathVariable("id") long id, HttpServletResponse response) {
        try {
            postService.deletePostImage(id);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        return "redirect:/admin/posts";
    }
}

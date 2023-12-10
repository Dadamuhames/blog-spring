package com.example.demo.controllers.admin;

import com.example.demo.models.Category;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import com.example.demo.dto.CategoryDto;
import com.example.demo.services.CategoryService;
import com.example.demo.utils.Paginate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryService categoryService;

    // categories list
    @GetMapping({"", "/"})
    public String categoryList(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int pageSize,
                               @RequestParam(defaultValue = "") String q) {

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<CategoryDto> categories = categoryService.findAllByTitleContainingOrderByIdDesc(q, pageable);

        int totalPages = categories.getTotalPages();

        List<Integer> pagination = Paginate.get_pagination(totalPages, page);

        model.addAttribute("objects", categories);
        model.addAttribute("pages", pagination);
        model.addAttribute("totalPages", totalPages);

        return "admin/categories/list";
    }


    // category create form get
    @GetMapping("/create")
    public String getCreateForm(Model model) {
        Category category = new Category();

        model.addAttribute("category", category);

        return "admin/categories/create";
    }


    // category create form post
    @PostMapping("/create")
    public String postCreateForm(@Valid  @ModelAttribute("category") CategoryDto categoryDto, BindingResult result) {
        if(result.hasErrors()) {
            return "admin/categories/create";
        }

        categoryService.saveCategory(categoryDto);

        return "redirect:/admin/categories";
    }


    // category update form get
    @GetMapping("/{id}/update")
    public String getUpdateForm(Model model, @PathVariable("id") long id) {
        CategoryDto categoryDto = categoryService.findById(id);

        model.addAttribute("category", categoryDto);

        return "admin/categories/update";
    }


    // category update form post
    @PostMapping("/{id}/update")
    public String postUpdateForm(@Valid @ModelAttribute("category") CategoryDto categoryDto,
                                 @PathVariable("id") long id,
                                 BindingResult result) {
        categoryDto.setId(id);

        if(result.hasErrors()) {
            return "admin/categories/update";
        }

        categoryService.saveCategory(categoryDto);

        return "redirect:/admin/categories";
    }


    // delete category
    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable("id") long id) {
        categoryService.deleteCategory(id);

        return "redirect:/admin/categories";
    }
}

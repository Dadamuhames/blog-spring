package com.example.demo.services;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDto findById(Long id);

    Page<CategoryDto> findAllByTitleContainingOrderByIdDesc(String title, Pageable pageable);

    void saveCategory(CategoryDto categoryDto);

    void deleteCategory(long id);

    List<CategoryDto> findAll();
}

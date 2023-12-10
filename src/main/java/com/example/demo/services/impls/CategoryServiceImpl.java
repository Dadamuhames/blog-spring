package com.example.demo.services.impls;

import com.example.demo.dto.CategoryDto;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Category;
import com.example.demo.repo.CategoryRepository;
import com.example.demo.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    public final CategoryRepository categoryRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map((category) -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public Page<CategoryDto> findAllByTitleContainingOrderByIdDesc(String title, Pageable pageable) {
        Page<Category> categories = categoryRepository.findAllByTitleContainingOrderByIdDesc(title, pageable);

        AtomicInteger index = new AtomicInteger();

        return categories.map((category) -> {
            int ind = index.getAndIncrement();
            CategoryDto eventDto = modelMapper.map(category, CategoryDto.class);

            eventDto.setIndex(ind + 1);

            return eventDto;
        });
    }

    @Override
    public void saveCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);

        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
}

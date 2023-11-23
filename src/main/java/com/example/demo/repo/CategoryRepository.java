package com.example.demo.repo;

import com.example.demo.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByTitleContainingOrderByIdDesc(String title, Pageable pageable); // for search by title
}

package com.example.demo.repo;

import com.example.demo.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // posts repository
    List<Post> findByActive(Boolean active); // where active

    List<Post> findByActiveAndTop(Boolean active, Boolean top); // where top, active

    @EntityGraph(attributePaths = {"category"})
    Page<Post> findAllByTitleContainingOrderByIdDesc(String title, Pageable pageable); // for search by title
}

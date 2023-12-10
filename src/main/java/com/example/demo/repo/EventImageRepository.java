package com.example.demo.repo;

import com.example.demo.models.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface EventImageRepository extends JpaRepository<EventImage, Long> {
    Set<EventImage> findByEventId(long id);
}

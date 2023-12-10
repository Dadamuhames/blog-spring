package com.example.demo.repo;

import com.example.demo.models.Event;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT * FROM event WHERE title LIKE %:q% OR subtitle LIKE %:q% ORDER BY id DESC", nativeQuery = true)
    Page<Event> searchEvents(@Param("q") String q, Pageable pageable);

//    @Query(value = "SELECT e.*, ei.event_id, ei.image FROM event e LEFT JOIN event_image ei ON ei.event_id = e.id WHERE e.id = :id", nativeQuery = true)

    @EntityGraph(attributePaths = {"images"})
    Optional<Event> findById(@Param("id") long id);
}

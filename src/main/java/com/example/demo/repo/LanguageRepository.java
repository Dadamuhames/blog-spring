package com.example.demo.repo;

import com.example.demo.models.Language;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    @Query(value = "SELECT * FROM language WHERE is_default = 1 LIMIT 1", nativeQuery = true)
    Optional<Language> findDefault();

    @Query(value = "SELECT * FROM language WHERE active = 1 ORDER BY is_default DESC", nativeQuery = true)
    List<Language> findActives();

    @Query(value = "SELECT * FROM language WHERE active = 1 AND id != :excludeId LIMIT 1", nativeQuery = true)
    Optional<Language> findFirstActive(@PathVariable("excludeId") Long excludeId);

    @Query(value = "SELECT * FROM language WHERE is_default = 1", nativeQuery = true)
    List<Language> findDefaults();
}

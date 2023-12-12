package com.example.demo.repo;

import com.example.demo.models.Translation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
    @Query(value = "SELECT transl.*, group.sub_text FROM translation" +
            " JOIN translation_group ON transl.group_id = translation_group.id" +
            " WHERE group_id = :id AND concat(group.sub_text, '.', transl.keyword) LIKE %:q% OR transl.value LIKE %:q% ORDER BY id DESC", nativeQuery = true)
    List<Translation> findByGroupId(@Param("q") String q, @Param("id") long id);

    @Query(value = "SELECT translation.*, translation_group.sub_text FROM translation" +
            " LEFT JOIN translation_group ON translation.group_id = translation_group.id" +
            " WHERE concat(translation_group.sub_text, '.', translation.keyword) LIKE %:q% OR translation.value LIKE %:q%", nativeQuery = true)
    Page<Translation> search(Pageable pageable, @Param("q") String q);
}

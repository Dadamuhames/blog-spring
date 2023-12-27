package com.example.demo.repo;

import com.example.demo.models.Translation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
    @Query(value = "SELECT translation.*, translation_group.sub_text FROM translation" +
            " JOIN translation_group ON translation.group_id = translation_group.id" +
            " WHERE translation.group_id = :id AND ( concat(translation_group.sub_text, '.', translation.keyword) LIKE %:q% " +
            " OR translation.value LIKE %:q% ) ORDER BY id DESC", nativeQuery = true)
    List<Translation> findByGroupId(@Param("q") String q, @Param("id") long id);

    List<Translation> findByGroupId(long id);

    @Query(value = "SELECT translation.*, translation_group.sub_text FROM translation" +
            " LEFT JOIN translation_group ON translation.group_id = translation_group.id" +
            " WHERE concat(translation_group.sub_text, '.', translation.keyword) LIKE %:q% OR translation.value LIKE %:q%", nativeQuery = true)
    Page<Translation> search(Pageable pageable, @Param("q") String q);

    @EntityGraph(attributePaths = {"group"})
    Optional<Translation> findById(@Param("id") long id);

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM translation WHERE keyword = %:keyword% AND group_id = %:groupId% AND id != %:id%) THEN true ELSE false END", nativeQuery = true)
    long existsByKeyword(@Param("keyword") String keyword, @Param("groupId") long groupId, @Param("id") long id);


    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM translation WHERE keyword = %:keyword% AND group_id = %:groupId%) THEN true ELSE false END", nativeQuery = true)
    long existsByKeyword(@Param("keyword") String keyword, @Param("groupId") long groupId);


    @EntityGraph(attributePaths = {"group"})
    List<Translation> findAll();
}

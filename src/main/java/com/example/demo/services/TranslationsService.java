package com.example.demo.services;

import com.example.demo.dto.TranslationDto;
import com.example.demo.dto.TranslationsWrapped;
import com.example.demo.models.TranslationGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TranslationsService {
    public Page<TranslationDto> searchTranslations(Pageable pageable, String q);

    public TranslationDto findById(long id);

    public List<TranslationDto> findByGroupId(String q, long group_id);

    public List<TranslationDto> findByGroupId(long group_id);

    public TranslationDto save(TranslationDto translationDto);

    public void saveAll(TranslationsWrapped translationsWrapped, TranslationGroup translationGroup);

    public Map<Integer, Map<String, String>> validateWrapped(TranslationsWrapped translationsWrapped);

    public void delete(long id);
}

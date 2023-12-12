package com.example.demo.services;

import com.example.demo.dto.TranslationDto;
import com.example.demo.dto.TranslationsWrapped;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TranslationsService {
    public Page<TranslationDto> searchTranslations(Pageable pageable, String q);

    public List<TranslationDto> findByGroupId(String q, long group_id);

    public void saveAll(TranslationsWrapped translationsWrapped);
}

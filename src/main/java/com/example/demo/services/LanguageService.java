package com.example.demo.services;

import com.example.demo.dto.LangDto;
import com.example.demo.models.Language;

import java.util.List;

public interface LanguageService {
    public Language findDefault();
    public List<LangDto> findAll();
    public Language findById(long id);
    public void deleteLanguage(long id);
    public void saveLanguage(Language language);
}

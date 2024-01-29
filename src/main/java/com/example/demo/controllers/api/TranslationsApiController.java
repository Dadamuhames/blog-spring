package com.example.demo.controllers.api;

import com.example.demo.mappers.TranslationsLocaleMapper;
import com.example.demo.models.Translation;
import com.example.demo.repo.TranslationRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/translations")
@RequiredArgsConstructor
public class TranslationsApiController {
    private final TranslationRepository translationRepository;
    private final TranslationsLocaleMapper translationsLocaleMapper;

    @GetMapping({"", "/"})
    public Map<Object, Object> all(HttpServletRequest request) {
        List<Translation> translations = translationRepository.findAll();

        return translationsLocaleMapper.mapAllToLocalized(translations, request);
    }
}

package com.example.demo.mappers;

import com.example.demo.models.Language;
import com.example.demo.services.LanguageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class LocaleMapper {
    private final LanguageService languageService;

    public String map(Map<String, String> value, HttpServletRequest request) {
        String language = request.getHeader("Language");
        Language defaultLanguage = languageService.findDefault();

        if(language == null || language.isBlank()) {

            if(defaultLanguage == null) {
                return "";
            }

            language = defaultLanguage.getCode();
        }

        String defaultOutput = value.getOrDefault(defaultLanguage.getCode(), "");

        String output = value.getOrDefault(language, defaultOutput);

        return Objects.equals(output, "") ? defaultOutput : output;
    }
}

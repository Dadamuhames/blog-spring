package com.example.demo.mappers;

import com.example.demo.models.Translation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TranslationsLocaleMapper {
    private final LocaleMapper localeMapper;

    public Map<String, String> mapAllToLocalized(List<Translation> translations, HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();

        for(Translation translation : translations) {
            String key = translation.getKeyword() + "." + translation.getGroup().getSubText();
            String value = localeMapper.map(translation.getValue(), request);
            result.put(key, value);
        }

        return result;
    }
}

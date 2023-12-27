package com.example.demo.mappers;

import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class LocaleMapper {
    public String map(Map<String, String> value, String language) {
        String result = value.get(language);

        if(result == "" || result == null) {
            return "";
        }

        return result;
    }
}

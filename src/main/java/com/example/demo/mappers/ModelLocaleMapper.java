package com.example.demo.mappers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ModelLocaleMapper {
    private final LocaleMapper localeMapper;

    public Map<Object, Object> map(Object obj, HttpServletRequest httpServletRequest) {
        Map<Object, Object> map = new HashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            Object value;
            String key = field.getName();

            try {
                value = field.get(obj);

                if(value instanceof Map<?,?>) {

                    Map<String, String> vl = (Map<String, String>) value;

                    value = localeMapper.map(vl, httpServletRequest);
                }

                map.put(key, value);
            }
            catch (Exception e) { }
        }
        return map;
    }
}

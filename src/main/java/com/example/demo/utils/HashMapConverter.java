package com.example.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        String value = null;
        try {
            value = objectMapper.writeValueAsString(stringObjectMap);
        } catch (final JsonProcessingException e) {
            value = "";
        }

        return value;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        Map<String, Object> customerInfo = null;
        try {
            customerInfo = objectMapper.readValue(s,
                    new TypeReference<HashMap<String, Object>>() {});
        } catch (final IOException e) {
            customerInfo = new HashMap<>();
        }

        return customerInfo;
    }
}

package com.example.demo.dto;

import com.example.demo.models.TranslationGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationDto {
    private int index;

    private Long id;

    private String keyword;

    private Map<String, String> value;

    private TranslationGroup group;
}

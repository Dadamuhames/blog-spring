package com.example.demo.dto;

import com.example.demo.models.TranslationGroup;
import com.example.demo.validators.annotations.JsonFieldConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotEmpty(message = "This field is required")
    @NotNull(message = "This field is required")
    private String keyword;

    @JsonFieldConstraint
    private Map<String, String> value;

    @NotNull(message = "Group cannot be blank")
    private Long group_id;

    private TranslationGroup group;
}

package com.example.demo.validators;

import com.example.demo.validators.annotations.JsonFieldConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;

public class JsonFieldValidate implements ConstraintValidator<JsonFieldConstraint, Map<String, Object>> {
    @Override
    public boolean isValid(Map map, ConstraintValidatorContext constraintValidatorContext) {
        String defaultLang = "ru";

        Object value = map.get(defaultLang);

        return value != null && !value.equals("");
    }
}

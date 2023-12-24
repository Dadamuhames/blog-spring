package com.example.demo.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ErrorMapper {
    public Map<String, String> mapErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if(bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for(FieldError error : fieldErrors) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
        }

        return errors;
    }
}

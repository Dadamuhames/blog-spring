package com.example.demo.controllerAdvices;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceNotFoundExceptionApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


// exception handler
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException ex) {
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("message", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(ResourceNotFoundExceptionApi.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundExceptionForApi(ResourceNotFoundExceptionApi ex) {
        Map<String, String> errorMessage = new HashMap<>();

        errorMessage.put("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}

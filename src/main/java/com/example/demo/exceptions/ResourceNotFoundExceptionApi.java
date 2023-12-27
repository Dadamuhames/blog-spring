package com.example.demo.exceptions;

public class ResourceNotFoundExceptionApi extends RuntimeException {
    public ResourceNotFoundExceptionApi(String message) {
        super(message);
    }
}

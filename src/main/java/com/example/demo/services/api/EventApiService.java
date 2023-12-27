package com.example.demo.services.api;

import com.example.demo.dto.apiDtos.EventApiDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventApiService {
    public EventApiDto findById(long id, HttpServletRequest request);

    public Page<EventApiDto> findAll(Pageable pageable, HttpServletRequest request);
}

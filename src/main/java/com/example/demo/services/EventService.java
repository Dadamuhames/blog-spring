package com.example.demo.services;


import com.example.demo.dto.EventDto;
import com.example.demo.dto.apiDtos.EventApiDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    public EventDto findById(long id);

    public Page<EventApiDto> listEvents(String q, Pageable pageable, HttpServletRequest request);

    public Page<EventDto> searchEvents(String q, Pageable pageable);

    void saveEvent(EventDto eventDto, HttpSession session);

    void deleteEvent(long id);
}

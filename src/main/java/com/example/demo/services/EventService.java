package com.example.demo.services;


import com.example.demo.dto.EventDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    public EventDto findById(long id);

    public Page<EventDto> searchEvents(String q, Pageable pageable);

    void saveEvent(EventDto eventDto, HttpSession session);

    void deleteEvent(long id);
}

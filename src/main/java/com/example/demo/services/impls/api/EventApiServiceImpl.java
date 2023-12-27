package com.example.demo.services.impls.api;

import com.example.demo.dto.apiDtos.EventApiDto;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceNotFoundExceptionApi;
import com.example.demo.mappers.EventLocaleMapper;
import com.example.demo.models.Event;
import com.example.demo.repo.EventImageRepository;
import com.example.demo.repo.EventRepository;
import com.example.demo.services.api.EventApiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventApiServiceImpl implements EventApiService {
    private final EventRepository eventRepository;
    private final EventLocaleMapper eventLocaleMapper;


    @Override
    public EventApiDto findById(long id, HttpServletRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptionApi("Event not found"));

        return eventLocaleMapper.mapOneToLocalized(event, request);
    }

    @Override
    public Page<EventApiDto> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Event> events = eventRepository.findAll(pageable);

        return eventLocaleMapper.mapPageToLocalized(events, request);
    }
}

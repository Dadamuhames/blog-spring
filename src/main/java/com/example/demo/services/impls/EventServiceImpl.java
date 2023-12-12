package com.example.demo.services.impls;

import com.example.demo.dto.EventDto;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Event;
import com.example.demo.models.EventImage;
import com.example.demo.repo.EventImageRepository;
import com.example.demo.repo.EventRepository;
import com.example.demo.services.EventService;
import com.example.demo.services.FileGetService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    private final FileGetService fileGetService;

    private final EventImageRepository eventImageRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public EventDto findById(long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        return modelMapper.map(event, EventDto.class);
    }

    @Override
    public Page<EventDto> searchEvents(String q, Pageable pageable) {
        Page<Event> events = eventRepository.searchEvents(q, pageable);

        int startIndex = (pageable.getPageNumber() - 1) * pageable.getPageSize();

        AtomicInteger index = new AtomicInteger(startIndex);

        return events.map((event) -> {
            int ind = index.getAndIncrement();
            EventDto eventDto = modelMapper.map(event, EventDto.class);

            eventDto.setIndex(ind + 1);

            return eventDto;
        });
    }

    @Override
    @Transactional
    public void saveEvent(EventDto eventDto, HttpSession session) {
        Event event = modelMapper.map(eventDto, Event.class);
        eventRepository.save(event);

        List<String> images = fileGetService.getImages(session, "eventImages");

        List<EventImage> eventImages = images.stream().map((image) -> {
            return EventImage.builder().image(image)
                    .event(event).build();
        }).toList();

        eventImageRepository.saveAll(eventImages);
    }

    @Override
    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }
}

package com.example.demo.services.impls;

import com.example.demo.dto.EventDto;
import com.example.demo.dto.apiDtos.EventApiDto;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.mappers.LocaleMapper;
import com.example.demo.models.Event;
import com.example.demo.models.EventImage;
import com.example.demo.repo.EventImageRepository;
import com.example.demo.repo.EventRepository;
import com.example.demo.services.EventService;
import com.example.demo.services.FileGetService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    private final FileGetService fileGetService;

    private final EventImageRepository eventImageRepository;

    private final LocaleMapper localeMapper;

    ModelMapper modelMapper = new ModelMapper();


    @Override
    public EventDto findById(long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        return modelMapper.map(event, EventDto.class);
    }

    @Override
    public Page<EventApiDto> listEvents(String q, Pageable pageable, HttpServletRequest request) {
        Page<Event> events = eventRepository.searchEvents(q, pageable);

        return events.map((event) -> {
                EventApiDto eventApiDto = modelMapper.map(event, EventApiDto.class);

                eventApiDto.setTitle(localeMapper.map(event.getTitle(), "ru"));
                eventApiDto.setSubtitle(localeMapper.map(event.getSubtitle(), "ru"));
                eventApiDto.setDescription(localeMapper.map(event.getDescription(), "ru"));

                Set<EventImage> eventImages = event.getImages();

                eventImages = eventImages.stream().peek((image)
                        -> image.setImage(fileGetService.getPhotoUrlShow(image.getImage(), request, 300, 300))
                ).collect(Collectors.toSet());

                eventApiDto.setImages(eventImages);

                return eventApiDto;
            }
        );
    }

    @Override
    public Page<EventDto> searchEvents(String q, Pageable pageable) {
        Page<Event> events = eventRepository.searchEvents(q, pageable);

        int startIndex = pageable.getPageNumber() * pageable.getPageSize();

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

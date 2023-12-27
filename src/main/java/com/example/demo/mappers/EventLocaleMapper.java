package com.example.demo.mappers;

import com.example.demo.dto.apiDtos.EventApiDto;
import com.example.demo.models.Event;
import com.example.demo.models.EventImage;
import com.example.demo.services.FileGetService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class EventLocaleMapper {
    private final LocaleMapper localeMapper;

    private final FileGetService fileGetService;

    public EventApiDto mapOneToLocalized(Event event, HttpServletRequest request) {
        EventApiDto eventApiDto = EventApiDto.builder()
                .id(event.getId())
                .title(localeMapper.map(event.getTitle(), request))
                .subtitle(localeMapper.map(event.getSubtitle(), request))
                .description(localeMapper.map(event.getDescription(), request))
                .build();


        Set<EventImage> images = event.getImages();

        for(EventImage image : images) {
            image.setImage(fileGetService.getPhotoUrlShow(image.getImage(), request, 300, 300));
        }

        eventApiDto.setImages(images);

        return  eventApiDto;
    }


    public Page<EventApiDto> mapPageToLocalized(Page<Event> events, HttpServletRequest request) {
        return events.map((event) -> mapOneToLocalized(event, request));
    }
}

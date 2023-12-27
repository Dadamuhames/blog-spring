package com.example.demo.controllers.api;

import com.example.demo.dto.apiDtos.EventApiDto;
import com.example.demo.services.api.EventApiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventsApiController {
    private final EventApiService eventApiService;

    @GetMapping({"", "/"})
    public Page<EventApiDto> list(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int pageSize,
                                  HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return eventApiService.findAll(pageable, request);
    }


    @GetMapping({"/{id}", "/{id}/"})
    public EventApiDto one(@PathVariable("id") long id, HttpServletRequest request) {
        return eventApiService.findById(id, request);
    }
}

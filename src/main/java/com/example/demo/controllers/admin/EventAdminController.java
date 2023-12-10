package com.example.demo.controllers.admin;

import com.example.demo.dto.EventDto;
import com.example.demo.models.EventImage;
import com.example.demo.repo.EventImageRepository;
import com.example.demo.services.EventService;
import com.example.demo.services.FileGetService;
import com.example.demo.services.FileUploadService;
import com.example.demo.services.impls.OtpServiceImpl;
import com.example.demo.utils.Paginate;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventAdminController {
    private final EventService eventService;
    private final FileGetService fileGetService;
    private final FileUploadService fileUploadService;
    private final EventImageRepository eventImageRepository;
    private final OtpServiceImpl.EnumerateMapper enumerateMapper;

    @GetMapping({"", "/"})
    public String eventsList(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "20") int pageSize,
                             @RequestParam(defaultValue = "") String q) {

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<EventDto> events = eventService.searchEvents(q, pageable);

        int totalPages = events.getTotalPages();

        List<Integer> pagination = Paginate.get_pagination(totalPages, page);

        model.addAttribute("objects", events);
        model.addAttribute("pages", pagination);
        model.addAttribute("totalPages", totalPages);

        return "admin/events/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model, HttpSession session) {
        EventDto eventDto = new EventDto();

        model.addAttribute("event", eventDto);

        fileUploadService.deleteFiles("eventImages", session);

        return "admin/events/create";
    }

    @PostMapping("/create")
    public String postCreateForm(@Valid @ModelAttribute("event") EventDto eventDto,
                                 BindingResult result,
                                 HttpSession session, Model model) {
        if(result.hasErrors()) {

            List<String> images = fileGetService.getImages(session, "eventImages");

            model.addAttribute("requestImages", images);

            return "admin/events/create";
        }

        eventService.saveEvent(eventDto, session);

        session.setAttribute("eventImages", Collections.emptyList());

        return "redirect:/admin/events";
    }


    @GetMapping("/{id}/update")
    public String getEditForm(Model model,
                              @PathVariable("id") long id,
                              HttpSession session) {

        EventDto eventDto = eventService.findById(id);

        model.addAttribute("event", eventDto);

        fileUploadService.deleteFiles("eventImages", session);

        return "admin/events/update";
    }


    @PostMapping("/{id}/update")
    public String postEditForm(@PathVariable("id") long id,
                               @Valid @ModelAttribute("event") EventDto eventDto,
                               BindingResult result,
                               HttpSession session, Model model) {

        eventDto.setId(id);

        if (result.hasErrors()) {

            List<String> images = fileGetService.getImages(session, "eventImages");

            Set<EventImage> eventImages = eventImageRepository.findByEventId(id);

            eventDto.setImages(eventImages);

            model.addAttribute("requestImages", enumerateMapper.enumerate(images));
            model.addAttribute("event", eventDto);

            return "admin/events/update";
        }

        eventService.saveEvent(eventDto, session);
        session.setAttribute("eventImages", Collections.emptyList());

        return "redirect:/admin/events";
    }

    @PostMapping("/{imageId}/deleteImage")
    @ResponseBody
    public Map<String, Object> deleteImage(@PathVariable("imageId") long imageId, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();

        try {
            eventImageRepository.deleteById(imageId);
        } catch (Exception e) {
            result.put("error", e.getMessage());
            response.setStatus(403);
        }

        return result;
    }

    @PostMapping("/{id}/delete")
    public String deleteEvent(@PathVariable("id") long id) {

        eventService.deleteEvent(id);

        return "redirect:/admin/events";
    }


}
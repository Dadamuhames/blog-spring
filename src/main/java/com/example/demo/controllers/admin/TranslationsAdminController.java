package com.example.demo.controllers.admin;

import com.example.demo.dto.EventDto;
import com.example.demo.dto.TranslationDto;
import com.example.demo.repo.TranslationGroupRepository;
import com.example.demo.services.TranslationsService;
import com.example.demo.utils.ErrorMapper;
import com.example.demo.utils.Paginate;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin/translations")
@RequiredArgsConstructor
public class TranslationsAdminController {
    private final TranslationGroupRepository translationGroupRepository;
    private final TranslationsService translationsService;
    private final ErrorMapper errorMapper;

    @GetMapping({"", "/"})
    public String list(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "20") int pageSize,
                       @RequestParam(defaultValue = "") String q) {


        Pageable pageable = PageRequest.of(page, pageSize);

        Page<TranslationDto> translations = translationsService.searchTranslations(pageable, q);

        int totalPages = translations.getTotalPages();

        List<Integer> pagination = Paginate.get_pagination(totalPages, page);

        model.addAttribute("objects", translations);
        model.addAttribute("pages", pagination);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("groups", translationGroupRepository.findAll());

        return "admin/transls/list";
    }


    // get translation detail
    @GetMapping({"/{id}", "/{id}/"})
    @ResponseBody
    public TranslationDto getTranslation(@PathVariable("id") long id) {
        return translationsService.findById(id);
    }

    // update translation
    @PostMapping("/{id}")
    @ResponseBody
    public Map<String, Object> updateTranslation(@PathVariable("id") long id,
                                                 @Valid TranslationDto translationDto,
                                                 BindingResult bindingResult,
                                                 HttpServletResponse response) {

        Map<String, Object> result = new HashMap<>();

        translationDto.setId(id);

        if(bindingResult.hasErrors()) {
            Map<String, String> errors = errorMapper.mapErrors(bindingResult);

            result.put("errors", errors);
            response.setStatus(403);

            return result;
        }

        TranslationDto translationUpdated = translationsService.save(translationDto);

        result.put("object", translationUpdated);

        return result;
    }


    @PostMapping("/{id}/delete")
    public String deleteTranslation(@PathVariable("id") long id,
                                    @RequestParam(defaultValue = "/admin/translations") String url,
                                    RedirectAttributes redirectAttributes) {
        try {
            translationsService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error with deletion");
        }

        System.out.println(url);

        return "redirect:" + url;
    }
}

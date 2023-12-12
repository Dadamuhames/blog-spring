package com.example.demo.controllers.admin;

import com.example.demo.dto.EventDto;
import com.example.demo.dto.TranslationDto;
import com.example.demo.repo.TranslationGroupRepository;
import com.example.demo.services.TranslationsService;
import com.example.demo.utils.Paginate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/translations")
@RequiredArgsConstructor
public class TranslationsAdminController {
    private final TranslationGroupRepository translationGroupRepository;
    private final TranslationsService translationsService;

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
}

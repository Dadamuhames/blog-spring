package com.example.demo.controllers.admin;

import com.example.demo.dto.LangDto;
import com.example.demo.models.Language;
import com.example.demo.repo.LanguageRepository;
import com.example.demo.services.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/langs")
@RequiredArgsConstructor
public class LanguageAdminController {
    private final LanguageService languageService;

    @GetMapping({"", "/"})
    public String list(Model model) {
        List<LangDto> langs = languageService.findAll();

        model.addAttribute("languages", langs);

        return "admin/langs/list";
    }

    @GetMapping("/create")
    public String createGet(Model model) {
        Language lang = new Language();

        model.addAttribute("lang", lang);

        return "admin/langs/create";
    }

    @PostMapping("/create")
    public String postCreateForm(Model model,
                                 @Valid @ModelAttribute("lang") Language lang,
                                 BindingResult result) {

        if(result.hasErrors()) {
            model.addAttribute("lang", lang);

            return "admin/langs/create";
        }

        languageService.saveLanguage(lang);

        return "redirect:/admin/langs";
    }

    @GetMapping("/{id}/update")
    public String getUpdateForm(@PathVariable("id") long id, Model model) {
        Language language = languageService.findById(id);

        model.addAttribute("lang", language);

        return "admin/langs/update";
    }


    @PostMapping("/{id}/update")
    public String postUpdateForm(@PathVariable("id") long id,
                                 @Valid @ModelAttribute("lang") Language lang,
                                 BindingResult result,
                                 Model model) {

        lang.setId(id);

        if(result.hasErrors()) {
            model.addAttribute("lang", lang);

            return "admin/langs/update";
        }

        languageService.saveLanguage(lang);

        return "redirect:/admin/langs";
    }
}

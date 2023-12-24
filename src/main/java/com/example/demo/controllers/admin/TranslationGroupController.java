package com.example.demo.controllers.admin;

import com.example.demo.dto.TranslationDto;
import com.example.demo.dto.TranslationsWrapped;
import com.example.demo.models.TranslationGroup;
import com.example.demo.repo.TranslationGroupRepository;
import com.example.demo.services.TranslationGroupService;
import com.example.demo.services.TranslationsService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/translations/groups")
@RequiredArgsConstructor
public class TranslationGroupController {
    private final TranslationGroupRepository translationGroupRepository;
    private final TranslationsService translationsService;
    private final TranslationGroupService translationGroupService;

    @PostMapping("/create")
    @ResponseBody
    public Map<String, Object> createGroup(@Valid @ModelAttribute("group") TranslationGroup translationGroup,
                                           BindingResult bindingResult,
                                           HttpServletResponse response) {

        Map<String, Object> result = new HashMap<>();

        if(bindingResult.hasErrors()) {
            result.put("error", bindingResult.getAllErrors());
            response.setStatus(403);

            return result;
        }

        try{
            TranslationGroup instance = translationGroupRepository.save(translationGroup);

            result.put("object", instance);
            response.setStatus(201);

        } catch (DataIntegrityViolationException e) {
            result.put("error", "Group with this title or key already exists");
            response.setStatus(403);

        } catch (Exception e) {
            result.put("error", e.getMessage());
            response.setStatus(403);
        }

        return result;
    }

    // translations group detail
    @GetMapping("/{id}")
    public String groupTranslations(Model model,
                                    @PathVariable("id") long id,
                                    @RequestParam(defaultValue = "") String q) {


        // group
        TranslationGroup translationGroup = translationGroupService.findById(id);

        // group translations
        List<TranslationDto> translations = translationsService.findByGroupId(q, id);

        // groups
        List<TranslationGroup> translationGroups = translationGroupRepository.findAll();

        model.addAttribute("objects", translations);
        model.addAttribute("groups", translationGroups);
        model.addAttribute("group", translationGroup);

        return "admin/transls/group";
    }


    // translations group edit form get
    @GetMapping("/{id}/edit")
    public String getEditForm(Model model,
                              @PathVariable("id") long id) {


        // group
        TranslationGroup translationGroup = translationGroupService.findById(id);
        // group translations
        List<TranslationDto> translations = translationsService.findByGroupId(id);

        model.addAttribute("group", translationGroup);

        TranslationsWrapped translationsWrapped = new TranslationsWrapped();

        translationsWrapped.setTranslations(translations);

        model.addAttribute("object", translationsWrapped);

        return "admin/transls/edit";
    }


    @PostMapping("/{id}/edit")
    public String postEditForm(@Valid @ModelAttribute("object") TranslationsWrapped translationsWrapped,
                               @PathVariable("id") long id,
                               Model model) {

        TranslationGroup translationGroup = translationGroupService.findById(id);

        List<TranslationDto> translations = translationsWrapped.getTranslations().stream().peek((transl) -> transl.setGroup_id(id)).toList();

        translationsWrapped.setTranslations(translations);

        Map<Integer, Map<String, String>> errors = translationsService.validateWrapped(translationsWrapped);

        if(!errors.isEmpty()) {
            model.addAttribute("object", translationsWrapped);
            model.addAttribute("group", translationGroup);
            model.addAttribute("errors", errors);

            return "admin/transls/edit";
        }

        try {
            translationsService.saveAll(translationsWrapped, translationGroup);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("!!!!");
            System.out.println(e.getMessage());
        }

        return String.format("redirect:/admin/translations/groups/%d", id);
    }
}

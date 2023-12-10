package com.example.demo.services.impls;

import com.example.demo.dto.EventDto;
import com.example.demo.dto.LangDto;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Language;
import com.example.demo.repo.LanguageRepository;
import com.example.demo.services.LanguageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository languageRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public Language findDefault() {
        return languageRepository.findDefault().orElse(null);
    }

    @Override
    public List<LangDto> findAll() {
        List<Language> languages = languageRepository.findAll();

        AtomicInteger index = new AtomicInteger();

        return languages.stream().map((lang) -> {
            int ind = index.getAndIncrement();
            LangDto eventDto = modelMapper.map(lang, LangDto.class);

            eventDto.setIndex(ind + 1);

            return eventDto;
        }).toList();
    }

    @Override
    public Language findById(long id) {
        return languageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    @Override
    @Transactional
    public void deleteLanguage(long id) {
        Language language = findById(id);

        languageRepository.deleteById(id);

        if(language.isDefault()) {
            Optional<Language> firstActiveOpt = languageRepository.findFirstActive(id);

            if(firstActiveOpt.isPresent()) {
                Language firstActive = firstActiveOpt.get();

                firstActive.setDefault(true);
                languageRepository.save(firstActive);
            }
        }
    }

    @Override
    @Transactional
    public void saveLanguage(Language language) {
        if(language.isDefault()) {
            List<Language> allDefaults = languageRepository.findDefaults();

            for(Language lang : allDefaults) {
                lang.setDefault(false);
            }

            languageRepository.saveAll(allDefaults);
        }

        languageRepository.save(language);
    }
}

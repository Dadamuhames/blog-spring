package com.example.demo.services.impls;

import com.example.demo.dto.TranslationDto;
import com.example.demo.dto.TranslationsWrapped;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Translation;
import com.example.demo.models.TranslationGroup;
import com.example.demo.repo.TranslationGroupRepository;
import com.example.demo.repo.TranslationRepository;
import com.example.demo.services.TranslationsService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationsService {
    private final TranslationRepository translationRepository;
    private final TranslationGroupRepository translationGroupRepository;
    private final Validator validator;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public Page<TranslationDto> searchTranslations(Pageable pageable, String q) {
        Page<Translation> translations = translationRepository.search(pageable, q);

        int startIndex = pageable.getPageNumber() * pageable.getPageSize();

        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());

        AtomicInteger index = new AtomicInteger(startIndex);

        return translations.map((translation) -> {
            int ind = index.getAndIncrement();
            TranslationDto translationDto = modelMapper.map(translation, TranslationDto.class);

            translationDto.setIndex(ind + 1);

            return translationDto;
        });
    }

    @Override
    public TranslationDto findById(long id) {
        Translation translation = translationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Event not found"));

        return modelMapper.map(translation, TranslationDto.class);
    }

    @Override
    public List<TranslationDto> findByGroupId(String q, long group_id) {
        List<Translation> translations = translationRepository.findByGroupId(q, group_id);

        AtomicInteger index = new AtomicInteger();

        return translations.stream().map((transl) -> {
            int ind = index.getAndIncrement();
            TranslationDto translationDto = modelMapper.map(transl, TranslationDto.class);

            translationDto.setIndex(ind + 1);

            return translationDto;
        }).toList();
    }

    @Override
    public List<TranslationDto> findByGroupId(long group_id) {
        List<Translation> translations = translationRepository.findByGroupId(group_id);

        return translations.stream().map((transl) -> modelMapper.map(transl, TranslationDto.class)).toList();
    }

    @Override
    public TranslationDto save(TranslationDto translationDto) {
        Translation translation = modelMapper.map(translationDto, Translation.class);

        TranslationGroup translationGroup = translationGroupRepository.findById(translationDto.getGroup_id()).orElse(null);

        translation.setGroup(translationGroup);

        translation = translationRepository.save(translation);

        return modelMapper.map(translation, TranslationDto.class);
    }


    @Override
    @Transactional
    public void saveAll(TranslationsWrapped translationsWrapped, TranslationGroup translationGroup) {
        List<Translation> translations = translationsWrapped.getTranslations().stream().map((transl) -> {
            Translation translation = modelMapper.map(transl, Translation.class);

            translation.setGroup(translationGroup);

            return translation;
        }).toList();

        translationRepository.saveAll(translations);
    }

    @Override
    @Transactional
    public Map<Integer, Map<String, String>> validateWrapped(TranslationsWrapped translationsWrapped) {
        Map<Integer, Map<String, String>> errors = new HashMap<>();

        List<TranslationDto> translations = translationsWrapped.getTranslations();

        for(int i = 0; i < translations.size(); i++) {
            TranslationDto translationDto = translations.get(i);

            long exists;

            if(translationDto.getId() == null) {
                exists = translationRepository.existsByKeyword(translationDto.getKeyword(),
                        translationDto.getGroup_id());
            } else {
                exists = translationRepository.existsByKeyword(translationDto.getKeyword(),
                        translationDto.getGroup_id(), translationDto.getId());
            }

            if(exists != 0) {
                Map<String, String> error = new HashMap<>();
                error.put("keyword", "Field should be unique");
                errors.put(i, error);
            }

            Set<ConstraintViolation<TranslationDto>> violations = validator.validate(translationDto);

            if(!violations.isEmpty()) {
                ConstraintViolation<TranslationDto> v = violations.stream().findFirst().orElse(null);

                Map<String, String> error = new HashMap<>();

                String fieldName = v.getPropertyPath().toString();
                String message = v.getMessage();

                error.put(fieldName, message);

                errors.put(i, error);
            }
        }

        return errors;
    }

    @Override
    public void delete(long id) {
        translationRepository.deleteById(id);
    }
}

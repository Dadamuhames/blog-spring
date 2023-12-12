package com.example.demo.services.impls;

import com.example.demo.dto.TranslationDto;
import com.example.demo.dto.TranslationsWrapped;
import com.example.demo.models.Translation;
import com.example.demo.repo.TranslationRepository;
import com.example.demo.services.TranslationsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationsService {
    private final TranslationRepository translationRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public Page<TranslationDto> searchTranslations(Pageable pageable, String q) {
        Page<Translation> translations = translationRepository.search(pageable, q);

        int startIndex = (pageable.getPageNumber() - 1) * pageable.getPageSize();

        AtomicInteger index = new AtomicInteger(startIndex);

        return translations.map((translation) -> {
            int ind = index.getAndIncrement();
            TranslationDto translationDto = modelMapper.map(translation, TranslationDto.class);

            translationDto.setIndex(ind + 1);

            return translationDto;
        });
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
    @Transactional
    public void saveAll(TranslationsWrapped translationsWrapped) {
        List<Translation> translations = translationsWrapped.getTranslations();

        translationRepository.saveAll(translations);
    }
}

package com.example.demo.services.impls;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.TranslationGroup;
import com.example.demo.repo.TranslationGroupRepository;
import com.example.demo.services.TranslationGroupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TranslationGroupServiceImpl implements TranslationGroupService {
    private final TranslationGroupRepository translationGroupRepository;

    @Override
    public TranslationGroup findById(long id) {
        return translationGroupRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Translation group not found")
        );
    }
}

package com.example.demo.dto;

import com.example.demo.models.Translation;
import lombok.*;

import java.util.List;


@Getter
@Setter
public class TranslationsWrapped {
    private List<Translation> translations;
}

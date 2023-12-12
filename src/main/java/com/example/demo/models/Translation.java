package com.example.demo.models;

import com.example.demo.utils.HashMapConverter;
import com.example.demo.validators.annotations.JsonFieldConstraint;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Translation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String keyword;

    @Column(columnDefinition = "json")
    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    private Map<String, String> value;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private TranslationGroup group;
}

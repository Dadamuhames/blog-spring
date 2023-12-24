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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"group_id", "keyword"})})
public class Translation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String keyword;

    @Column(columnDefinition = "json")
    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    private Map<String, String> value;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private TranslationGroup group;
}

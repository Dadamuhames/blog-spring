package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TranslationGroup {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @NotNull
    @Column(unique = true)
    private String name;

    @NotEmpty
    @NotNull
    @Column(unique = true)
    private String subText;

    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Translation> translations = new HashSet<>();
}

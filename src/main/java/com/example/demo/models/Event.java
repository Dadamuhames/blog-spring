package com.example.demo.models;

import com.example.demo.utils.HashMapConverter;
import com.example.demo.validators.annotations.JsonFieldConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "json")
    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    private Map<String, Object> title;

    @Column(columnDefinition = "json")
    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    private Map<String, Object> subtitle;

    @Column(columnDefinition = "json")
    @Convert(converter = HashMapConverter.class)
    @JsonFieldConstraint
    private Map<String, Object> description;


    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<EventImage> images = new HashSet<>();
}

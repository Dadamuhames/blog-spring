package com.example.demo.dto;

import com.example.demo.models.EventImage;
import com.example.demo.validators.annotations.JsonFieldConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private int index;

    public Long id;

    @JsonFieldConstraint
    public Map<String, String> title;

    @JsonFieldConstraint
    private Map<String, String> subtitle;

    @JsonFieldConstraint
    private Map<String, String> description;

    private Set<EventImage> images;
}

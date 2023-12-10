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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {
    private int index;

    public Long id;

    @JsonFieldConstraint
    public Map<String, Object> title;

    @JsonFieldConstraint
    private Map<String, Object> subtitle;

    @JsonFieldConstraint
    private Map<String, Object> description;

    private Set<EventImage> images;
}

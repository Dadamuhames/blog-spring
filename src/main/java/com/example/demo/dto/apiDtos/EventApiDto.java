package com.example.demo.dto.apiDtos;

import com.example.demo.models.EventImage;
import com.example.demo.validators.annotations.JsonFieldConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventApiDto {
    private Long id;

    private String title;

    private String subtitle;

    private String description;

    private Set<EventImage> images;
}

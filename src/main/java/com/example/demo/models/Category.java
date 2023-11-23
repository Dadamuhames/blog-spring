package com.example.demo.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
    // fields
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Title cannot be blank")
    private String title;

    private String hex = "#FF0000";

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private Set<Post> posts = new HashSet<>();
}

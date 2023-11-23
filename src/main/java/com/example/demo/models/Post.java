package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post {
    // fields
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Title cannot be blank")
    private String title;

    @NotEmpty(message = "Title cannot be blank")
    private String subtitle;

    private String photoUrl;
    private String text;

    @Min(0)
    private int views = 0;

    private boolean active = true;
    private boolean top = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private  LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}

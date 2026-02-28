package com.class2000.reunion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    @Column(length = 5000)
    private String content;

    private String author;

    @Builder.Default
    private LocalDateTime publishedAt = LocalDateTime.now();

    private String category; // e.g. ANNOUNCEMENT, MEMORY, MILESTONE
    private String imageUrl;
}

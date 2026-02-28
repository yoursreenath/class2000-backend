package com.class2000.reunion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "photos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String imageUrl;

    private String uploadedBy;

    @Builder.Default
    private LocalDateTime uploadedAt = LocalDateTime.now();

    private String tags; // comma-separated
}

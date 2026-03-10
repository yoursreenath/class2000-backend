package com.class2000.reunion.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "initiatives")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Initiative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private String type;       // infrastructure, support, donation, event

    private String badge;

    private String imageUrl;

    @Column(name = "initiative_year")
    private String year;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}

package com.class2000.reunion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private LocalDateTime eventDate;

    private String location;

    @Column(length = 2000)
    private String description;

    private String organizer;

    @Builder.Default
    private Integer rsvpCount = 0;
}

package com.class2000.reunion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "get_togethers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTogether {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;           // e.g. "1st Get-Together"

	@Column(columnDefinition = "TEXT")
    private String description;     // short description shown on card

    private String location;        // e.g. "Bangalore"

    private String eventYear;       // e.g. "2005"

    private String coverImageUrl;   // the one image shown on the section card

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}

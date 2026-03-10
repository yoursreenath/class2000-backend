package com.class2000.reunion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    private String lastName;

    private String subject;        // Subject they taught
    private String designation;    // e.g. Head of Department, Class Teacher
    private String currentCity;
    private String currentStatus;  // e.g. Retired, Still Teaching, Passed Away

    @Column(length = 2000)
    private String bio;

    private String photoUrl;

    @Email
    private String email;
    private String phone;

    private String yearsAtSchool;  // e.g. "1990 - 2005"
}

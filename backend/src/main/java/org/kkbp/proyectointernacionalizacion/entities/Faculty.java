package org.kkbp.proyectointernacionalizacion.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "faculty")
@Getter
@Setter
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultyId;

    @Column(nullable = false)
    @Size(max = 100)
    private String facultyName;

    @Column(nullable = false)
    @Pattern(regexp = "F[A-Z]{2,5}")
    private String facultyAbbreviation;
}

package org.kkbp.proyectointernacionalizacion.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "career")
@Getter
@Setter
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long careerId;

    @Column(nullable = false)
    @NotBlank(message = "El nombre de la carrera es obligatorio")
    @Size(max = 120, message = "El nombre no puede exceder los 120 caracteres")
    private String careerName;

    @OneToOne(mappedBy = "coordinatedCareer")
    @JoinColumn(name = "coordinator_id")
    private User coordinator;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    @NotNull(message = "La facultad es obligatoria")
    private Faculty faculty;
}

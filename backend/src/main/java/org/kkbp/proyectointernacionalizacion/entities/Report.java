package org.kkbp.proyectointernacionalizacion.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Entity
@Table(name = "report")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "coordinator_id", nullable = false)
    @NotNull(message = "El coordinador es obligatorio")
    private User coordinator;

    @Column(name = "career_name", nullable = false)
    @NotBlank(message = "El nombre de la carrera es obligatorio")
    @Size(max = 100, message = "El nombre de la carrera no puede exceder 100 caracteres")
    private String careerName;

    @Column(name = "faculty_name", nullable = false)
    @NotBlank(message = "El nombre de la facultad es obligatorio")
    @Size(max = 100, message = "El nombre de la facultad no puede exceder 100 caracteres")
    private String facultyName;

    @Column(name = "faculty_abbreviation", nullable = false)
    @NotBlank(message = "La abreviatura de la facultad es obligatoria")
    @Size(max = 10, message = "La abreviatura de la facultad no puede exceder 10 caracteres")
    private String facultyAbbreviation;

    @Column(nullable = false)
    @NotBlank(message = "El título del reporte es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String title;

    @Column(nullable = false)
    @NotNull(message = "La fecha es obligatoria")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable = false)
    @Min(value = 0, message = "El número de estudiantes enviados no puede ser negativo")
    private int sentStudents;

    @Column(nullable = false)
    @Min(value = 0, message = "El número de estudiantes recibidos no puede ser negativo")
    private int receivedStudents;

    @Column(nullable = false)
    @Min(value = 0, message = "El número de estudiantes en modo virtual no puede ser negativo")
    private int virtualModeStudents;

    @Column(nullable = false)
    @Min(value = 0, message = "El número de profesores enviados no puede ser negativo")
    private int sentTeachers;

    @Column(nullable = false)
    @Min(value = 0, message = "El número de profesores recibidos no puede ser negativo")
    private int receivedTeachers;

    @Column(nullable = false)
    @Min(value = 0, message = "El número de profesores en modo virtual no puede ser negativo")
    private int virtualModeTeachers;

    @Column(nullable = false)
    @Min(value = 0, message = "El número de nuevos convenios no puede ser negativo")
    private int newAgreements;

    @Column(nullable = false)
    @Min(value = 0, message = "El número de eventos no puede ser negativo")
    private int events;

    @Column(nullable = false)
    @NotBlank(message = "Los desafíos son obligatorios")
    @Size(max = 1000, message = "Los desafíos no pueden exceder 1000 caracteres")
    private String challenges;

    @Column(nullable = false)
    @NotBlank(message = "Los requerimientos son obligatorios")
    @Size(max = 1000, message = "Los requerimientos no pueden exceder 1000 caracteres")
    private String requirements;

    @Column(nullable = false)
    @NotBlank(message = "Las sugerencias son obligatorias")
    @Size(max = 1000, message = "Las sugerencias no pueden exceder 1000 caracteres")
    private String suggestions;
}

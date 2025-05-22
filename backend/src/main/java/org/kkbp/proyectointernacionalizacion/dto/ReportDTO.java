package org.kkbp.proyectointernacionalizacion.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private Long reportId;

    @NotNull(message = "El coordinador es obligatorio")
    private Long coordinatorId;

    @NotBlank(message = "El título del reporte es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String title;

    @NotNull(message = "La fecha es obligatoria")
    private Date date;

    @Min(value = 0, message = "El número de estudiantes enviados no puede ser negativo")
    private int sentStudents;

    @Min(value = 0, message = "El número de estudiantes recibidos no puede ser negativo")
    private int receivedStudents;

    @Min(value = 0, message = "El número de estudiantes en modo virtual no puede ser negativo")
    private int virtualModeStudents;

    @Min(value = 0, message = "El número de profesores enviados no puede ser negativo")
    private int sentTeachers;

    @Min(value = 0, message = "El número de profesores recibidos no puede ser negativo")
    private int receivedTeachers;

    @Min(value = 0, message = "El número de profesores en modo virtual no puede ser negativo")
    private int virtualModeTeachers;

    @Min(value = 0, message = "El número de nuevos convenios no puede ser negativo")
    private int newAgreements;

    @Min(value = 0, message = "El número de eventos no puede ser negativo")
    private int events;

    @NotBlank(message = "Los desafíos son obligatorios")
    @Size(max = 1000, message = "Los desafíos no pueden exceder 1000 caracteres")
    private String challenges;

    @NotBlank(message = "Los requerimientos son obligatorios")
    @Size(max = 1000, message = "Los requerimientos no pueden exceder 1000 caracteres")
    private String requirements;

    @NotBlank(message = "Las sugerencias son obligatorias")
    @Size(max = 1000, message = "Las sugerencias no pueden exceder 1000 caracteres")
    private String suggestions;
}

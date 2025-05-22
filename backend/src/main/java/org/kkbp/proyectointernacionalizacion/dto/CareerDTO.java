package org.kkbp.proyectointernacionalizacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CareerDTO {
    private Long careerId;

    @NotBlank(message = "El nombre de la carrera es obligatorio")
    @Size(max = 120, message = "El nombre no puede exceder los 120 caracteres")
    private String careerName;

    private Long coordinatorId;

    @NotNull(message = "El ID de la facultad es obligatorio")
    private Long facultyId;
}

package org.kkbp.proyectointernacionalizacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FacultyDTO {
    private Long facultyId;

    @NotBlank(message = "El nombre de la facultad no puede estar vacío.")
    @Size(max = 100, message = "El nombre de la facultad no puede exceder los 100 caracteres.")
    private String facultyName;

    @NotBlank(message = "Es obligatorio especificar la abreviatura.")
    @Pattern(regexp = "F[A-Z]{2,5}", message = "La abreviatura debe iniciar con F y tener entre 3-6 letras.")
    private String facultyAbbreviation;
}

package org.kkbp.proyectointernacionalizacion.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kkbp.proyectointernacionalizacion.entities.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;

    @NotBlank(message = "Los nombres del usuario son obligatorios")
    @Size(max = 100, message = "Los nombres del usuario no deben exceder los 100 caracteres")
    private String names;

    @NotBlank(message = "Los apellidos del usuario son obligatorios")
    @Size(max = 100, message = "Los apellidos del usuario no deben exceder los 100 caracteres")
    private String surnames;

    @NotBlank(message = "El correo del usuario es obligatorio")
    @Email(message = "El formato del correo no es válido")
    private String email;

    @NotBlank(message = "Es obligatorio definir una contraseña para el coordinador")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
    private String password;

    @NotNull(message = "Debe especificarse la carrera del coordinador")
    private Long coordinatedCareerId;

    private Role role;
}

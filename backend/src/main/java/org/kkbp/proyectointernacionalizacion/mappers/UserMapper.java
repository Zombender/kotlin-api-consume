package org.kkbp.proyectointernacionalizacion.mappers;

import org.kkbp.proyectointernacionalizacion.dto.UserDTO;
import org.kkbp.proyectointernacionalizacion.entities.Career;
import org.kkbp.proyectointernacionalizacion.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "coordinatedCareerId", source = "coordinatedCareer.careerId")
    UserDTO toDTO(User user);

    @Mapping(target = "role", source = "userDTO.role")
    @Mapping(target = "coordinatedCareer", source = "career")
    @Mapping(target = "faculty", source = "career.faculty")
    @Mapping(target = "userId", ignore = true)
    User toEntity(UserDTO userDTO, Career career);
}

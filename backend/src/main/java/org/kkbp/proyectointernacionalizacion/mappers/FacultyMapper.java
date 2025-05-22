package org.kkbp.proyectointernacionalizacion.mappers;

import org.kkbp.proyectointernacionalizacion.dto.FacultyDTO;
import org.kkbp.proyectointernacionalizacion.entities.Faculty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FacultyMapper {
    FacultyDTO toDTO(Faculty faculty);

    @Mapping(target = "facultyId", ignore = true)
    Faculty toEntity(FacultyDTO facultyDTO);
}

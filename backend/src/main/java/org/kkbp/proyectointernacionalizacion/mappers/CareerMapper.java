package org.kkbp.proyectointernacionalizacion.mappers;

import jakarta.persistence.EntityNotFoundException;
import org.kkbp.proyectointernacionalizacion.dto.CareerDTO;
import org.kkbp.proyectointernacionalizacion.entities.Career;
import org.kkbp.proyectointernacionalizacion.entities.Faculty;
import org.kkbp.proyectointernacionalizacion.repositories.FacultyRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CareerMapper {

    @Mapping(target = "coordinatorId", source = "coordinator.userId")
    @Mapping(target = "facultyId", source = "faculty.facultyId")
    CareerDTO toDTO(Career career);

    @Mapping(target = "coordinator", ignore = true)
    @Mapping(target = "faculty", source = "facultyId", qualifiedByName = "idToFaculty")
    Career toEntity(CareerDTO dto, @Context FacultyRepository facultyRepository);

    @Named("idToFaculty")
    static Faculty mapFacultyIdToFaculty(Long facultyId, @Context FacultyRepository facultyRepository) {
        if (facultyId == null) {
            throw new IllegalArgumentException("El ID de facultad no puede ser nulo");
        }
        return facultyRepository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("Facultad no encontrada con ID: " + facultyId));
    }
}

package org.kkbp.proyectointernacionalizacion.mappers;

import org.kkbp.proyectointernacionalizacion.dto.ReportDTO;
import org.kkbp.proyectointernacionalizacion.entities.Report;
import org.kkbp.proyectointernacionalizacion.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    @Mapping(target = "coordinatorId", source = "coordinator.userId")
    ReportDTO toDTO(Report report);

    @Mapping(target = "reportId", ignore = true)
    @Mapping(target = "date", source = "reportDTO.date")
    Report toEntity(ReportDTO reportDTO, User coordinator, String careerName,
                    String facultyName, String facultyAbbreviation);
}

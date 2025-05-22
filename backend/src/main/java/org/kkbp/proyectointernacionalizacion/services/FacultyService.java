package org.kkbp.proyectointernacionalizacion.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.kkbp.proyectointernacionalizacion.dto.FacultyDTO;
import org.kkbp.proyectointernacionalizacion.entities.Faculty;
import org.kkbp.proyectointernacionalizacion.mappers.FacultyMapper;
import org.kkbp.proyectointernacionalizacion.repositories.CareerRepository;
import org.kkbp.proyectointernacionalizacion.repositories.FacultyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final CareerRepository careerRepository;
    private final FacultyMapper facultyMapper;

    @Transactional(readOnly = true)
    public List<FacultyDTO> getAllFaculties() {
        return facultyRepository.findAll().stream().map(facultyMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public FacultyDTO getFacultyById(Long id) {
        return facultyRepository.findById(id).map(facultyMapper::toDTO).orElseThrow(
                () -> new EntityNotFoundException("Facultad con id " + id + " no encontrada"));
    }

    @Transactional
    public FacultyDTO saveFaculty(FacultyDTO facultyDTO) {
        validateFacultyUniqueness(facultyDTO, null);
        Faculty savedFaculty = facultyRepository.save(facultyMapper.toEntity(facultyDTO));
        return facultyMapper.toDTO(savedFaculty);
    }

    @Transactional
    public FacultyDTO updateFaculty(Long id, FacultyDTO facultyDTO) {
        Faculty existingFaculty = this.getExistingFaculty(id);
        validateFacultyUniqueness(facultyDTO, id);
        existingFaculty.setFacultyName(facultyDTO.getFacultyName());
        existingFaculty.setFacultyAbbreviation(facultyDTO.getFacultyAbbreviation());
        Faculty updatedFaculty = facultyRepository.save(existingFaculty);
        return facultyMapper.toDTO(updatedFaculty);
    }

    @Transactional
    public void deleteFaculty(Long id) {
        Faculty existingFaculty = this.getExistingFaculty(id);
        if (careerRepository.existsByFaculty_FacultyId(id)) {
            throw new IllegalStateException("No se puede eliminar la facultad porque tiene carreras asociadas");
        }
        facultyRepository.delete(existingFaculty);
    }

    private Faculty getExistingFaculty(Long id) {
        return facultyRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Facultad con id " + id + " no encontrada")
        );
    }

    private void validateFacultyUniqueness(FacultyDTO facultyDTO, Long excludeId) {
        if (excludeId == null) {
            if (facultyRepository.existsByFacultyName(facultyDTO.getFacultyName())) {
                throw new IllegalArgumentException("Ya existe una facultad con este nombre");
            }
            if (facultyRepository.existsByFacultyAbbreviation(facultyDTO.getFacultyAbbreviation())) {
                throw new IllegalArgumentException("Ya existe una facultad con esta abreviatura");
            }
        } else {
            if (facultyRepository.existsByFacultyNameAndFacultyIdNot(facultyDTO.getFacultyName(), excludeId)) {
                throw new IllegalArgumentException("Ya existe otra facultad con este nombre");
            }
            if (facultyRepository.existsByFacultyAbbreviationAndFacultyIdNot(facultyDTO.getFacultyAbbreviation(), excludeId)) {
                throw new IllegalArgumentException("Ya existe otra facultad con esta abreviatura");
            }
        }
    }
}

package org.kkbp.proyectointernacionalizacion.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.kkbp.proyectointernacionalizacion.dto.CareerDTO;
import org.kkbp.proyectointernacionalizacion.entities.Career;
import org.kkbp.proyectointernacionalizacion.mappers.CareerMapper;
import org.kkbp.proyectointernacionalizacion.repositories.CareerRepository;
import org.kkbp.proyectointernacionalizacion.repositories.FacultyRepository;
import org.kkbp.proyectointernacionalizacion.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerService {
    private final CareerRepository careerRepository;
    private final FacultyRepository facultyRepository;
    private final UserRepository userRepository;
    private final CareerMapper careerMapper;

    @Transactional(readOnly = true)
    public List<CareerDTO> getAllCareers() {
        return careerRepository.findAll().stream()
                .map(careerMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CareerDTO getCareerById(Long id) {
        return careerRepository.findById(id).map(careerMapper::toDTO).orElseThrow(
                () -> new EntityNotFoundException("Carrera con id " + id + " no encontrado"));
    }

    @Transactional
    public CareerDTO saveCareer(CareerDTO careerDTO) {
        validateCareerUniqueness(careerDTO, null);
        Career career = careerMapper.toEntity(careerDTO, facultyRepository);
        Career savedCareer = careerRepository.save(career);
        return careerMapper.toDTO(savedCareer);
    }

    @Transactional
    public CareerDTO updateCareer(Long id, CareerDTO careerDTO) {
        Career existingCareer = this.getExistingCareer(id);
        validateCareerUniqueness(careerDTO, id);
        existingCareer.setCareerName(careerDTO.getCareerName());
        existingCareer.setFaculty(facultyRepository.findById(careerDTO.getFacultyId())
                .orElseThrow(() -> new EntityNotFoundException("Facultad no encontrada")));
        Career updatedCareer = careerRepository.save(existingCareer);
        return careerMapper.toDTO(updatedCareer);
    }

    @Transactional
    public void deleteCareer(Long id) { careerRepository.deleteById(id); }

    private Career getExistingCareer(Long id) {
        return careerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Carrera con id " + id + "no encontrada"));
    }

    private void validateCareerUniqueness(CareerDTO careerDTO, Long excludeId) {
        if (excludeId == null) {
            if (careerRepository.existsByCareerName(careerDTO.getCareerName())) {
                throw new IllegalArgumentException("Ya existe una carrera con este nombre");
            }
        } else {
            if (careerRepository.existsByCareerNameAndCareerIdNot(careerDTO.getCareerName(), excludeId)) {
                throw new IllegalArgumentException("Ya existe otra carrera con este nombre");
            }
        }
    }
}

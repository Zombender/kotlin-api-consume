package org.kkbp.proyectointernacionalizacion.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.kkbp.proyectointernacionalizacion.dto.UserDTO;
import org.kkbp.proyectointernacionalizacion.entities.Career;
import org.kkbp.proyectointernacionalizacion.entities.Role;
import org.kkbp.proyectointernacionalizacion.entities.User;
import org.kkbp.proyectointernacionalizacion.mappers.UserMapper;
import org.kkbp.proyectointernacionalizacion.repositories.CareerRepository;
import org.kkbp.proyectointernacionalizacion.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CareerRepository careerRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDTO).orElseThrow(
                () -> new EntityNotFoundException("Usuario con id " + id + " no encontrado"));
    }

    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {
        if (userDTO.getRole() == Role.ADMIN) {
            throw new IllegalArgumentException("No se pueden crear usuarios con el rol ADMIN");
        }

        if (userDTO.getCoordinatedCareerId() == null) {
            throw new IllegalArgumentException("Se debe asignar una carrera al coordinador");
        } else if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("El email ya está en uso");
        }

        Career career = careerRepository.findById(userDTO.getCoordinatedCareerId())
                .orElseThrow(() -> new EntityNotFoundException("El id proporcionado no corresponde a ninguna carrera"));

        validateUniqueCoordinatorForCareer(career.getCareerId());

        User user = userMapper.toEntity(userDTO, career);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        career.setCoordinator(user);
        careerRepository.save(career);

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = this.getExistingUser(id);

        if (userDTO.getRole() == Role.ADMIN && existingUser.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("No se puede asignar rol ADMIN");
        }

        if (userRepository.existsByEmailAndUserIdNot(userDTO.getEmail(), userDTO.getUserId())) {
            throw new IllegalArgumentException("El email ya está en uso");
        }

        if (userDTO.getCoordinatedCareerId() == null) {
            throw new IllegalArgumentException("Se debe asignar una carrera al coordinador");
        }

        Career newCareer = careerRepository.findById(userDTO.getCoordinatedCareerId())
                .orElseThrow(() -> new EntityNotFoundException("Carrera no encontrada"));

        Career oldCareer = existingUser.getCoordinatedCareer();

        // Validar si está cambiando de carrera
        if (!newCareer.getCareerId().equals(oldCareer.getCareerId())) {
            validateUniqueCoordinatorForCareer(newCareer.getCareerId());

            // Limpiar coordinador de la antigua carrera
            oldCareer.setCoordinator(null);
            careerRepository.save(oldCareer);

            // Establecer nuevo coordinador
            newCareer.setCoordinator(existingUser);
            careerRepository.save(newCareer);
        }

        existingUser.setNames(userDTO.getNames());
        existingUser.setSurnames(userDTO.getSurnames());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setCoordinatedCareer(newCareer);
        existingUser.setFaculty(newCareer.getFaculty());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        return userMapper.toDTO(userRepository.save(existingUser));
    }

    @Transactional
    public void deleteUser(Long id) {
        User existingUser = this.getExistingUser(id);
        Career career = existingUser.getCoordinatedCareer();
        career.setCoordinator(null);
        careerRepository.save(career);
        userRepository.delete(existingUser);
    }

    private User getExistingUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Usuario con id " + id + " no encontrado"));
    }

    private void validateUniqueCoordinatorForCareer(Long careerId) {
        if (userRepository.existsByCoordinatedCareerCareerId(careerId)) {
            throw new IllegalStateException("Esta carrera ya tiene un coordinador asignado");
        }
    }
}

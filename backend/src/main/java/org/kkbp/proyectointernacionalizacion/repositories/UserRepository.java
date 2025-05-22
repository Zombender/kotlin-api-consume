package org.kkbp.proyectointernacionalizacion.repositories;

import org.kkbp.proyectointernacionalizacion.entities.Role;
import org.kkbp.proyectointernacionalizacion.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndUserIdNot(String email, Long id);
    boolean existsByCoordinatedCareerCareerId(Long careerId);
}

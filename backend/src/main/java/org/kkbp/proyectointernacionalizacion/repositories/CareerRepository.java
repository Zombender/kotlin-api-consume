package org.kkbp.proyectointernacionalizacion.repositories;

import org.kkbp.proyectointernacionalizacion.entities.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
    // Verifica si existe una carrera con el mismo nombre
    boolean existsByCareerName(String name);

    // Verifica si existe otra carrera con el mismo nombre (excluyendo un ID)
    boolean existsByCareerNameAndCareerIdNot(String name, Long excludeId);

    boolean existsByFaculty_FacultyId(Long facultyId);
}

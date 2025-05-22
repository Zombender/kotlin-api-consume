package org.kkbp.proyectointernacionalizacion.repositories;

import org.kkbp.proyectointernacionalizacion.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    // Verifica si existe una facultad con cierto nombre
    boolean existsByFacultyName(String name);

    // Verifica si ya existe una facultad con cierta abreviatura
    boolean existsByFacultyAbbreviation(String abbreviation);

    // Verifica si existe una facultad con cierto nombre, excluyendo un ID específico
    boolean existsByFacultyNameAndFacultyIdNot(String name, Long id);

    // Verifica si existe una facultad con cierta abreviatura, excluyendo un ID específico
    boolean existsByFacultyAbbreviationAndFacultyIdNot(String abbreviation, Long id);
}

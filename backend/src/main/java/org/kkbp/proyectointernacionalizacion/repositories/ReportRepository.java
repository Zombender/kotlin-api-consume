package org.kkbp.proyectointernacionalizacion.repositories;

import org.kkbp.proyectointernacionalizacion.entities.Report;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByCoordinatorUserId(Long coordinatorId);
    List<Report> findAll(Specification<Report> spec);
}

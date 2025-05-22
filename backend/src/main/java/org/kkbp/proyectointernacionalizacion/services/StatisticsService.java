package org.kkbp.proyectointernacionalizacion.services;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.kkbp.proyectointernacionalizacion.dto.StatisticsDTO;
import org.kkbp.proyectointernacionalizacion.dto.StatisticsFilter;
import org.kkbp.proyectointernacionalizacion.entities.Faculty;
import org.kkbp.proyectointernacionalizacion.entities.Report;
import org.kkbp.proyectointernacionalizacion.repositories.FacultyRepository;
import org.kkbp.proyectointernacionalizacion.repositories.ReportRepository;
import org.kkbp.proyectointernacionalizacion.repositories.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final FacultyRepository facultyRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    @Transactional(readOnly = true)
    public StatisticsDTO calculateStatistics(StatisticsFilter filter) {
        Specification<Report> spec = buildSpecification(filter);
        List<Report> reports = reportRepository.findAll(spec);

        StatisticsDTO statistics = new StatisticsDTO();

        statistics.setTotalReports(reports.size());
        statistics.setTotalSentStudents(reports.stream().mapToInt(Report::getSentStudents).sum());
        statistics.setTotalReceivedStudents(reports.stream().mapToInt(Report::getReceivedStudents).sum());
        statistics.setTotalVirtualStudents(reports.stream().mapToInt(Report::getVirtualModeStudents).sum());
        statistics.setTotalSentTeachers(reports.stream().mapToInt(Report::getSentTeachers).sum());
        statistics.setTotalReceivedTeachers(reports.stream().mapToInt(Report::getReceivedTeachers).sum());
        statistics.setTotalVirtualTeachers(reports.stream().mapToInt(Report::getVirtualModeTeachers).sum());
        statistics.setTotalNewAgreements(reports.stream().mapToInt(Report::getNewAgreements).sum());
        statistics.setTotalEvents(reports.stream().mapToInt(Report::getEvents).sum());

        statistics.setReportsByFaculty(calculateReportsByFaculty(reports));
        statistics.setReportsByCoordinator(calculateReportsByCoordinator(reports));

        statistics.setSentStudentsByFaculty(groupSumByFaculty(reports, Report::getSentStudents));
        statistics.setReceivedStudentsByFaculty(groupSumByFaculty(reports, Report::getReceivedStudents));
        statistics.setVirtualStudentsByFaculty(groupSumByFaculty(reports, Report::getVirtualModeStudents));

        statistics.setSentTeachersByFaculty(groupSumByFaculty(reports, Report::getSentTeachers));
        statistics.setReceivedTeachersByFaculty(groupSumByFaculty(reports, Report::getReceivedTeachers));
        statistics.setVirtualTeachersByFaculty(groupSumByFaculty(reports, Report::getVirtualModeTeachers));


        return statistics;
    }

    private Specification<Report> buildSpecification(StatisticsFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getFacultyId() != null) {
                Faculty faculty = facultyRepository.findById(filter.getFacultyId())
                        .orElseThrow(() -> new RuntimeException("Facultad no encontrada"));

                predicates.add(criteriaBuilder.equal(
                        root.get("facultyName"),
                        faculty.getFacultyName()
                ));
            }

            if (filter.getCoordinatorId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("coordinator").get("userId"),
                        filter.getCoordinatorId()));
            }

            if (filter.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("date"),
                        filter.getStartDate()));
            }

            if (filter.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("date"),
                        filter.getEndDate()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Map<String, Integer> calculateReportsByFaculty(List<Report> reports) {
        return reports.stream()
                .collect(Collectors.groupingBy(
                        Report::getFacultyName,
                        Collectors.summingInt(r -> 1)
                ));
    }

    private Map<String, Integer> calculateReportsByCoordinator(List<Report> reports) {
        return reports.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getCoordinator().getNames() + " " + r.getCoordinator().getSurnames(),
                        Collectors.summingInt(r -> 1)
                ));
    }

    private Map<String, Integer> groupSumByFaculty(List<Report> reports, ToIntFunction<Report> mapper) {
        return reports.stream()
                .collect(Collectors.groupingBy(
                        Report::getFacultyName,
                        Collectors.summingInt(mapper)
                ));
    }
}

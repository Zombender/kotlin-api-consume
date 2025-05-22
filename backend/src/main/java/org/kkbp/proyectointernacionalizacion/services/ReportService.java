package org.kkbp.proyectointernacionalizacion.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.kkbp.proyectointernacionalizacion.dto.ReportDTO;
import org.kkbp.proyectointernacionalizacion.entities.Report;
import org.kkbp.proyectointernacionalizacion.entities.User;
import org.kkbp.proyectointernacionalizacion.mappers.ReportMapper;
import org.kkbp.proyectointernacionalizacion.repositories.ReportRepository;
import org.kkbp.proyectointernacionalizacion.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ReportMapper reportMapper;

    @Transactional(readOnly = true)
    public List<ReportDTO> getAllReports() {
        return reportRepository.findAll().stream().map(reportMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ReportDTO getReportById(Long id) {
        return reportRepository.findById(id).map(reportMapper::toDTO).orElseThrow(
                () -> new EntityNotFoundException("Reporte no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<ReportDTO> getReportsByCoordinator(Long coordinatorId) {
        return reportRepository.findByCoordinatorUserId(coordinatorId).stream()
                .map(reportMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReportDTO saveReport(ReportDTO reportDTO) {
        User coordinator = userRepository.findById(reportDTO.getCoordinatorId())
                .orElseThrow(() -> new EntityNotFoundException("Coordinador con id " + reportDTO.getCoordinatorId() + " no encontrado"));

        // Obtener información de la carrera y facultad del coordinador
        String careerName = coordinator.getCoordinatedCareer().getCareerName();
        String facultyName = coordinator.getFaculty().getFacultyName();
        String facultyAbbreviation = coordinator.getFaculty().getFacultyAbbreviation();

        Report report = reportMapper.toEntity(
                reportDTO,
                coordinator,
                careerName,
                facultyName,
                facultyAbbreviation
        );

        Report savedReport = reportRepository.save(report);
        return reportMapper.toDTO(savedReport);
    }

    @Transactional
    public ReportDTO updateReport(Long id, ReportDTO reportDTO) {
        Report existingReport = this.getExistingReport(id);

        // Actualizar solo los campos permitidos
        existingReport.setTitle(reportDTO.getTitle());
        existingReport.setDate(reportDTO.getDate());
        existingReport.setSentStudents(reportDTO.getSentStudents());
        existingReport.setReceivedStudents(reportDTO.getReceivedStudents());
        existingReport.setVirtualModeStudents(reportDTO.getVirtualModeStudents());
        existingReport.setSentTeachers(reportDTO.getSentTeachers());
        existingReport.setReceivedTeachers(reportDTO.getReceivedTeachers());
        existingReport.setVirtualModeTeachers(reportDTO.getVirtualModeTeachers());
        existingReport.setNewAgreements(reportDTO.getNewAgreements());
        existingReport.setEvents(reportDTO.getEvents());
        existingReport.setChallenges(reportDTO.getChallenges());
        existingReport.setRequirements(reportDTO.getRequirements());
        existingReport.setSuggestions(reportDTO.getSuggestions());

        Report updatedReport = reportRepository.save(existingReport);
        return reportMapper.toDTO(updatedReport);
    }

    @Transactional
    public void deleteReport(Long id) {
        Report existingReport = this.getExistingReport(id);
        reportRepository.delete(existingReport);
    }

    private Report getExistingReport(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con ID: " + id));
    }
}

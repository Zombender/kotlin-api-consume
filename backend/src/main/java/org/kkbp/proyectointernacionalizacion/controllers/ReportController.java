package org.kkbp.proyectointernacionalizacion.controllers;

import jakarta.validation.Valid;
import org.kkbp.proyectointernacionalizacion.dto.ApiResponse;
import org.kkbp.proyectointernacionalizacion.dto.ReportDTO;
import org.kkbp.proyectointernacionalizacion.services.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5174",
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllReports() {
        List<ReportDTO> reports = reportService.getAllReports();
        ApiResponse response = new ApiResponse(
                "success",
                reports.isEmpty() ? "No hay reportes registrados" : null,
                reports
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getReportById(@PathVariable Long id) {
        ReportDTO report = reportService.getReportById(id);
        ApiResponse response = new ApiResponse("success", null, report);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/coordinator/{coordinatorId}")
    public ResponseEntity<ApiResponse> getReportsByCoordinatorId(@PathVariable Long coordinatorId) {
        List<ReportDTO> reports = reportService.getReportsByCoordinator(coordinatorId);
        ApiResponse response = new ApiResponse(
                "success",
                reports.isEmpty() ? "No se encontraron reportes asociados al id" : null,
                reports
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createReport(
            @Valid @RequestBody ReportDTO reportDTO) {
        ReportDTO savedReport = reportService.saveReport(reportDTO);
        ApiResponse response = new ApiResponse("success",
                "Reporte creado exitosamente",
                savedReport
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateReport(
            @PathVariable Long id,
            @Valid @RequestBody ReportDTO reportDTO) {
        ReportDTO updatedReport = reportService.updateReport(id, reportDTO);
        ApiResponse response = new ApiResponse(
                "success",
                "Reporte actualizado exitosamente",
                updatedReport
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        ApiResponse response = new ApiResponse(
                "success",
                "Reporte eliminado exitosamente",
                null
        );
        return ResponseEntity.ok(response);
    }
}

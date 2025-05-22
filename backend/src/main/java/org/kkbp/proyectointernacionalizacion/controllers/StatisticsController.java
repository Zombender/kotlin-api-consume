package org.kkbp.proyectointernacionalizacion.controllers;

import org.kkbp.proyectointernacionalizacion.dto.ApiResponse;
import org.kkbp.proyectointernacionalizacion.dto.StatisticsDTO;
import org.kkbp.proyectointernacionalizacion.dto.StatisticsFilter;
import org.kkbp.proyectointernacionalizacion.services.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "http://localhost:5174",
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getStatistics(
            @RequestParam(required = false) Long facultyId,
            @RequestParam(required = false) Long coordinatorId,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
        StatisticsFilter filter = new StatisticsFilter(facultyId, coordinatorId, startDate, endDate);
        StatisticsDTO statistics = statisticsService.calculateStatistics(filter);
        ApiResponse response = new ApiResponse("success", null, statistics);
        return ResponseEntity.ok(response);
    }
}

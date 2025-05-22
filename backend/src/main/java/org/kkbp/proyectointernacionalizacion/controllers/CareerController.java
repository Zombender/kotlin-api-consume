package org.kkbp.proyectointernacionalizacion.controllers;

import jakarta.validation.Valid;
import org.kkbp.proyectointernacionalizacion.dto.ApiResponse;
import org.kkbp.proyectointernacionalizacion.dto.CareerDTO;
import org.kkbp.proyectointernacionalizacion.services.CareerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5174",
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/careers")
public class CareerController {
    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCareers() {
        List<CareerDTO> careers = careerService.getAllCareers();
        ApiResponse response = new ApiResponse(
                "success",
                careers.isEmpty() ? "No hay carreras registradas" : null,
                careers
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCareerById(@PathVariable Long id) {
        CareerDTO career = careerService.getCareerById(id);
        ApiResponse response = new ApiResponse("success", null, career);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCareer(
            @Valid @RequestBody CareerDTO careerDTO) {
        CareerDTO savedCareer = careerService.saveCareer(careerDTO);
        ApiResponse response = new ApiResponse(
                "success",
                "Carrera creada exitosamente",
                savedCareer
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCareer(
            @PathVariable Long id,
            @Valid @RequestBody CareerDTO careerDTO) {
        CareerDTO updatedCareer = careerService.updateCareer(id, careerDTO);
        ApiResponse response = new ApiResponse(
                "success",
                "Carrera actualizada exitosamente",
                updatedCareer
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCareer(@PathVariable Long id) {
        careerService.deleteCareer(id);
        ApiResponse response = new ApiResponse(
                "success",
                "Carrera eliminada exitosamente",
                null
        );
        return ResponseEntity.ok(response);
    }
}

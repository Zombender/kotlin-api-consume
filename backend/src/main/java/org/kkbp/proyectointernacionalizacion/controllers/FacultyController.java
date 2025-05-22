package org.kkbp.proyectointernacionalizacion.controllers;

import jakarta.validation.Valid;
import org.kkbp.proyectointernacionalizacion.dto.ApiResponse;
import org.kkbp.proyectointernacionalizacion.dto.FacultyDTO;
import org.kkbp.proyectointernacionalizacion.services.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5174",
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/faculties")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllFaculties() {
        List<FacultyDTO> faculties = facultyService.getAllFaculties();
        ApiResponse response = new ApiResponse(
                "success",
                faculties.isEmpty() ? "No hay facultades registradas" : null,
                faculties
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getFacultyById(@PathVariable Long id) {
        FacultyDTO faculty = facultyService.getFacultyById(id);
        ApiResponse response = new ApiResponse("success", null, faculty);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createFaculty(
            @Valid @RequestBody FacultyDTO facultyDTO) {
        FacultyDTO savedFaculty = facultyService.saveFaculty(facultyDTO);
        ApiResponse response = new ApiResponse(
                "success",
                "Facultad creada exitosamente",
                savedFaculty
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateFaculty(
            @PathVariable Long id,
            @Valid @RequestBody FacultyDTO facultyDTO) {
        FacultyDTO updatedFaculty = facultyService.updateFaculty(id, facultyDTO);
        ApiResponse response = new ApiResponse(
                "success",
                "Facultad actualizada exitosamente",
                updatedFaculty
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        ApiResponse response = new ApiResponse(
                "success",
                "Facultad eliminada exitosamente",
                null
        );
        return ResponseEntity.ok(response);
    }
}

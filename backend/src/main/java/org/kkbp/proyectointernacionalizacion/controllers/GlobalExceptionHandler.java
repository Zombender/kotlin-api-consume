package org.kkbp.proyectointernacionalizacion.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.kkbp.proyectointernacionalizacion.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Error 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception e) {
        ApiResponse response = new ApiResponse("error", "Error interno del servidor", null);
        return ResponseEntity.status(500).body(response);
    }

    // Error 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEntityNotFound(EntityNotFoundException e) {
        ApiResponse response = new ApiResponse("error", e.getMessage(), null);
        return ResponseEntity.status(404).body(response);
    }

    // Error 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ApiResponse response = new ApiResponse("error", "Validación fallida", errors);
        return ResponseEntity.badRequest().body(response);
    }
}

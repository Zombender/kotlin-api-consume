package org.kkbp.proyectointernacionalizacion.controllers;

import jakarta.validation.Valid;
import org.kkbp.proyectointernacionalizacion.dto.ApiResponse;
import org.kkbp.proyectointernacionalizacion.dto.UserDTO;
import org.kkbp.proyectointernacionalizacion.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5174",
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        ApiResponse response = new ApiResponse(
                "success",
                users.isEmpty() ? "No hay usuarios registrados" : null,
                users
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        ApiResponse response = new ApiResponse("success", null, user);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.saveUser(userDTO);
        ApiResponse response = new ApiResponse("success",
                "Usuario creado exitosamente",
                savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        ApiResponse response = new ApiResponse(
                "success",
                "Usuario actualizado exitosamente",
                updatedUser
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ApiResponse response = new ApiResponse(
                "success",
                "Usuario eliminado exitosamente",
                null
        );
        return ResponseEntity.ok(response);
    }
}

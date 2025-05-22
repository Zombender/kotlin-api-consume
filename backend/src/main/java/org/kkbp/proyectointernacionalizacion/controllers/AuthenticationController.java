package org.kkbp.proyectointernacionalizacion.controllers;

import jakarta.validation.Valid;
import org.kkbp.proyectointernacionalizacion.configuration.JwtUtil;
import org.kkbp.proyectointernacionalizacion.dto.ApiResponse;
import org.kkbp.proyectointernacionalizacion.dto.AuthenticationRequest;
import org.kkbp.proyectointernacionalizacion.dto.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/api/login")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtUtil jwtUtil,
                                    UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createAuthenticationToken(
            @Valid @RequestBody AuthenticationRequest authenticationRequest) {

        try {
            // Autenticar al usuario
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );

            // Cargar detalles del usuario y generar token
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);

            // Crear respuesta exitosa
            AuthenticationResponse authResponse = new AuthenticationResponse(jwt);
            ApiResponse response = new ApiResponse(
                    "success",
                    "Autenticación exitosa (Tiempo de expiración: 6 horas)",
                    authResponse
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            // Credenciales inválidas
            ApiResponse response = new ApiResponse(
                    "error",
                    "Credenciales inválidas",
                    null
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        } catch (Exception e) {
            // Otros errores
            ApiResponse response = new ApiResponse(
                    "error",
                    "Error durante la autenticación: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
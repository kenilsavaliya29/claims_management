package com.claims.controller;

import com.claims.DTOs.Request.LoginRequestDTO;
import com.claims.DTOs.Request.RegisterRequestDTO;
import com.claims.DTOs.Response.LoginResponseDTO;
import com.claims.DTOs.Response.RegisterResponseDTO;
import com.claims.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public RegisterResponseDTO registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        return authService.register(registerRequestDTO);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        return authService.login(loginRequestDTO);
    }

}

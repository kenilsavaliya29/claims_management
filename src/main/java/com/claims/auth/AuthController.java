package com.claims.auth;

import com.claims.auth.dto.Request.LoginRequestDTO;
import com.claims.auth.dto.Request.RegisterRequestDTO;
import com.claims.auth.dto.Response.LoginResponseDTO;
import com.claims.auth.dto.Response.RegisterResponseDTO;
import com.claims.auth.service.AuthService;
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

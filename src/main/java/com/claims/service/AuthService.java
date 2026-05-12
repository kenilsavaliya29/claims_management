package com.claims.service;

import com.claims.DTOs.Request.LoginRequestDTO;
import com.claims.DTOs.Request.RegisterRequestDTO;
import com.claims.DTOs.Response.LoginResponseDTO;
import com.claims.DTOs.Response.RegisterResponseDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO);
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}

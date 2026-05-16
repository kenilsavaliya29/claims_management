package com.claims.auth.service;

import com.claims.auth.dto.Request.LoginRequestDTO;
import com.claims.auth.dto.Request.RegisterRequestDTO;
import com.claims.auth.dto.Response.LoginResponseDTO;
import com.claims.auth.dto.Response.RegisterResponseDTO;

public interface AuthService {
    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO);
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}

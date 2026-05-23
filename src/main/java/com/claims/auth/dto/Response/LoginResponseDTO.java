package com.claims.auth.dto.Response;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String message;

    private String token = null;

    private String role = null;

    public LoginResponseDTO(String message) {
        this.message = message;
    }

    public LoginResponseDTO(String message, String token, String role) {
        this.message = message;
        this.token = token;
        this.role = role;
    }

}

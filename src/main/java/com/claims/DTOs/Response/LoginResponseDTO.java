package com.claims.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private String message;

    private String token = null;

    public LoginResponseDTO(String message) {
        this.message = message;
    }

}

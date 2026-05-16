package com.claims.auth.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponseDTO {

    private String message;

    private String token;

    public RegisterResponseDTO(String message) {
        this.message = message;
    }

}

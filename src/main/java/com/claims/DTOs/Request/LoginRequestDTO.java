package com.claims.DTOs.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;
}


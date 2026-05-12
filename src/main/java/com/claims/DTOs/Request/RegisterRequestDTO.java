package com.claims.DTOs.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterRequestDTO {


    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;
}

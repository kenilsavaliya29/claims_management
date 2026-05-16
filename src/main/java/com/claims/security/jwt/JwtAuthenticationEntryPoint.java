package com.claims.security.jwt;

import com.claims.common.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class JwtAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException ) throws IOException {

        ApiResponse apiResponse =
                ApiResponse.builder()
                        .success(false)
                        .status(401)
                        .message("Invalid or missing token")
                        .data(new HashMap<>())
                        .build();

        response.setStatus(401);
        response.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(
                response.getOutputStream(),
                apiResponse
        );
    }
}
package com.claims.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {

    private boolean success;
    private int status;
    private String message;
    private Object data;
}

package com.claims.claim.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClaimCreateRequestDTO {

    private String claimType;
    private String title;
    private String description;

    private String incidentDate;
    private BigDecimal amount;
}

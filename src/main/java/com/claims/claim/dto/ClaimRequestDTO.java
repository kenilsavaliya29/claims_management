package com.claims.claim.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClaimRequestDTO {

    private String claimType;
    private String title;
    private String description;

    private String incidentDate;
    private BigDecimal amount;
}

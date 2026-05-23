package com.claims.admin.dto;

import com.claims.claim.ClaimStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClaimFilterRequestDTO {

    private int page = 0;
    private int size = 10;

    private String search;

    private ClaimStatus status;
    private String claimType;

    private String sortBy = "createdAt";
    private String sortDirection = "desc";

    private BigDecimal minAmount;
    private BigDecimal maxAmount;

    private LocalDate fromDate;
    private LocalDate toDate;
}
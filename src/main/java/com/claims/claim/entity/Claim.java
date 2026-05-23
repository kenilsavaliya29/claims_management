package com.claims.claim.entity;

import com.claims.claim.ClaimStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String claimId;
    private String claimType;
    private String title;
    private String description;

    private LocalDate incidentDate;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    private String createdBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String adminRemarks;
}

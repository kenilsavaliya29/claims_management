package com.claims.claim.dto;

import com.claims.claim.ClaimStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClaimResponseDTO {

    private String message;
    private String claimId;
    private ClaimStatus status;
    private LocalDateTime createdAt;

}

package com.claims.claim.dto.response;

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
public class ClaimCreateResponseDTO {

    private String message;
    private String claimId;
    private ClaimStatus status;
    private LocalDateTime createdAt;

}

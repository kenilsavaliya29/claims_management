package com.claims.claim.dto.response;

import com.claims.claim.ClaimStatus;
import com.claims.claim.entity.Claim;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClaimFetchResponseDTO {

    private String message;

    private Claim claim;
}
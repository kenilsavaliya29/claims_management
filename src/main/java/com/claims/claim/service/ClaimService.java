package com.claims.claim.service;

import com.claims.claim.dto.ClaimRequestDTO;
import com.claims.claim.dto.ClaimResponseDTO;
import com.claims.claim.entity.Claim;

public interface ClaimService {
    public ClaimResponseDTO create(ClaimRequestDTO requestDTO);
}

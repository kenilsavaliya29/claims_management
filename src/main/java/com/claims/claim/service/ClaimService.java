package com.claims.claim.service;

import com.claims.claim.dto.request.ClaimCreateRequestDTO;
import com.claims.claim.dto.response.ClaimFetchResponseDTO;
import com.claims.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface ClaimService {
    public ResponseEntity<ApiResponse> create(ClaimCreateRequestDTO requestDTO);
    public ResponseEntity<ApiResponse> fetchClaims(int page, int size);
    public ResponseEntity<ApiResponse> fetchClaimById(String claimId);
}

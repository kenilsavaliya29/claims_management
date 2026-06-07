package com.claims.claim.service;

import com.claims.claim.dto.request.ClaimCreateRequestDTO;
import com.claims.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface ClaimService {
    ResponseEntity<ApiResponse> create(ClaimCreateRequestDTO requestDTO);
    ResponseEntity<ApiResponse> fetchClaims(int page, int size);
    ResponseEntity<ApiResponse> fetchClaimById(String claimId);
}

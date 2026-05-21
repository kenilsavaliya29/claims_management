package com.claims.admin.service;

import com.claims.admin.dto.StatusUpdateRequestDTO;
import com.claims.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    public ResponseEntity<ApiResponse> fetchAllClaims();
    public ResponseEntity<ApiResponse> updateClaimById(String claimId, StatusUpdateRequestDTO statusUpdateRequestDTO);
}

package com.claims.admin.service.impl;

import com.claims.admin.dto.StatusUpdateRequestDTO;
import com.claims.admin.service.AdminService;
import com.claims.claim.ClaimStatus;
import com.claims.claim.entity.Claim;
import com.claims.claim.repository.ClaimRepository;
import com.claims.common.dto.ApiResponse;
import com.claims.common.exception.WrongStatusUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final ClaimRepository claimRepository;

    public AdminServiceImpl(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> fetchAllClaims() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String currentUser = authentication.getName();

        boolean isAdmin =  authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<Claim> claims;

        if(isAdmin) {
            claims = claimRepository.findAll();
            ApiResponse response = ApiResponse
                    .builder()
                    .success(true)
                    .status(200)
                    .message("Claims fetched Successfully")
                    .data(claims)
                    .build();

            return ResponseEntity.ok(response);
        }
        else{
            ApiResponse response = ApiResponse
                    .builder()
                    .success(false)
                    .status(400)
                    .message("Admin access required")
                    .build();

            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateClaimById(String claimId, StatusUpdateRequestDTO statusUpdateRequestDTO) {

        Optional<Claim> claimOptional = claimRepository.findByClaimId(claimId);
        Claim claim = claimOptional.get();

        if(statusUpdateRequestDTO.getStatus() == ClaimStatus.PENDING){
            throw new WrongStatusUpdate("Admin cannot change claim status to PENDING");
        }

        claim.setStatus(statusUpdateRequestDTO.getStatus());
        claim.setUpdatedAt(LocalDateTime.now());

        claimRepository.save(claim);
        ApiResponse response = ApiResponse.builder()
                .success(true)
                .status(200)
                .message("Claim Status Updated Successfully")
                .build();

        return ResponseEntity.ok(response);
    }
}

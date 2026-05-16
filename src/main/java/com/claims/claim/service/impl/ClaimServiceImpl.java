package com.claims.claim.service.impl;

import com.claims.claim.ClaimStatus;
import com.claims.claim.dto.request.ClaimCreateRequestDTO;
import com.claims.claim.entity.Claim;
import com.claims.claim.repository.ClaimRepository;
import com.claims.claim.service.ClaimService;
import com.claims.common.dto.ApiResponse;
import com.claims.common.exception.ClaimNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> create(ClaimCreateRequestDTO requestDTO) {

        String currentUser = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Claim claim = Claim
                .builder()
                .claimId(
                        "CLM-" +
                                UUID.randomUUID()
                                        .toString()
                                        .substring(0, 8)
                )
                .claimType(requestDTO.getClaimType())
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .incidentDate(LocalDate.parse(requestDTO.getIncidentDate()))
                .amount(requestDTO.getAmount())
                .status(ClaimStatus.PENDING)
                .createdBy(currentUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Claim savedClaim =
                claimRepository.save(claim);

        ApiResponse response =
                ApiResponse
                        .builder()
                        .success(true)
                        .status(201)
                        .message("Claim created successfully")
                        .data(savedClaim)
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Override
    public ResponseEntity<ApiResponse> fetchClaims() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();


        String currentUser = authentication.getName();

        boolean isAdmin =  authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<Claim> claims;

        if(isAdmin) {
            claims = claimRepository.findAll();
        }
        else{
            claims =  claimRepository.findByCreatedBy(currentUser);
        }

        ApiResponse response =
                ApiResponse
                        .builder()
                        .success(true)
                        .status(200)
                        .message("Claims fetched successfully")
                        .data(claims)
                        .build();

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse> fetchClaimById(String claimId) {

        String currentUser =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        Optional<Claim> claimOptional =
                claimRepository.findByClaimId(claimId);

        if (claimOptional.isPresent() && claimOptional.get().getCreatedBy().equals(currentUser)) {
            ApiResponse response =
                    ApiResponse
                            .builder()
                            .success(true)
                            .status(200)
                            .message("Claim found")
                            .data(claimOptional.get())
                            .build();

            return ResponseEntity.ok(response);
        }

        ApiResponse response =
                ApiResponse
                        .builder()
                        .success(false)
                        .status(404)
                        .message(
                                "Claim not found or unauthorized"
                        )
                        .data(new HashMap<>())
                        .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
}
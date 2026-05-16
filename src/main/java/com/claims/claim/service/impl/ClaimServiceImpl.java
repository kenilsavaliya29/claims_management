package com.claims.claim.service.impl;

import com.claims.claim.ClaimStatus;
import com.claims.claim.dto.ClaimRequestDTO;
import com.claims.claim.dto.ClaimResponseDTO;
import com.claims.claim.entity.Claim;
import com.claims.claim.repository.ClaimRepository;
import com.claims.claim.service.ClaimService;
import lombok.Builder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    public ClaimResponseDTO create(ClaimRequestDTO requestDTO) {

        String currentUser =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        Claim claim = Claim
                .builder()
                .claimId("CLM-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8))
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

        Claim savedClaim = claimRepository.save(claim);


        return ClaimResponseDTO
                .builder()
                .message("Claim created successfully")
                .claimId(savedClaim.getClaimId())
                .status(savedClaim.getStatus())
                .createdAt(savedClaim.getCreatedAt())
                .build();
    }

}

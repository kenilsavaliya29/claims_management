package com.claims.claim.controller;

import com.claims.claim.dto.request.ClaimCreateRequestDTO;
import com.claims.claim.dto.response.ClaimCreateResponseDTO;
import com.claims.claim.dto.response.ClaimFetchResponseDTO;
import com.claims.claim.entity.Claim;
import com.claims.claim.service.ClaimService;
import com.claims.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/claim")

public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody ClaimCreateRequestDTO requestDTO) {
        return claimService.create(requestDTO);
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse> fetchLoggedUserClaims(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return claimService.fetchClaims(page, size);
    }

    @GetMapping("/{claimId}")
    public ResponseEntity<ApiResponse> fetchLoggedUserClaimsById(@PathVariable("claimId") String claimId){
        return claimService.fetchClaimById(claimId);
    }
}

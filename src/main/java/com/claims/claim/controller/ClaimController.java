package com.claims.claim.controller;

import com.claims.claim.dto.ClaimRequestDTO;
import com.claims.claim.dto.ClaimResponseDTO;
import com.claims.claim.service.ClaimService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/claim")

public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping("/create")
    public ClaimResponseDTO create(@Valid @RequestBody ClaimRequestDTO requestDTO) {
        return claimService.create(requestDTO);
    }
}

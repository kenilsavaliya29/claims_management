package com.claims.admin.controller;

import com.claims.admin.dto.StatusUpdateRequestDTO;
import com.claims.admin.service.AdminService;
import com.claims.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/claims")
    public ResponseEntity<ApiResponse> fetchAllClaims() {
        return adminService.fetchAllClaims();
    }

    @PostMapping("/claim/{claimId}/status")
    public ResponseEntity<ApiResponse> updateClaimStatus(@Valid @RequestBody StatusUpdateRequestDTO statusUpdateRequestDTO, @PathVariable String claimId) {
        return adminService.updateClaimById(claimId, statusUpdateRequestDTO);
    }
}

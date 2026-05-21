package com.claims.admin.dto;

import com.claims.claim.ClaimStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateRequestDTO {

    @NotNull
    private ClaimStatus status;
    private String remarks;
}

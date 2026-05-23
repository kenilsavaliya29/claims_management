package com.claims.admin.service.impl;

import com.claims.admin.dto.ClaimFilterRequestDTO;
import com.claims.admin.dto.StatusUpdateRequestDTO;
import com.claims.admin.service.AdminService;
import com.claims.claim.ClaimStatus;
import com.claims.claim.entity.Claim;
import com.claims.claim.repository.ClaimRepository;
import com.claims.common.dto.ApiResponse;
import com.claims.common.exception.WrongStatusUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final ClaimRepository claimRepository;

    public AdminServiceImpl(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> fetchAllClaims(ClaimFilterRequestDTO filter) {

        Specification<Claim> specification = (
                (root, query, criteriaBuilder) ->
                        criteriaBuilder.conjunction()
        );

        if(filter.getStatus() != null) {
            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("status"), filter.getStatus())
                    );
        }

        if(filter.getSearch() != null && !filter.getSearch().isBlank()) {
            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.or(
                                    criteriaBuilder.like(root.get("claimId"),"%" + filter.getSearch() + "%"),
                                    criteriaBuilder.like(root.get("createdBy"), "%" + filter.getSearch() + "%"),
                                    criteriaBuilder.like(root.get("title"), "%" + filter.getSearch() + "%")
                            )
            );
        }

        if(filter.getClaimType() != null &&  !filter.getClaimType().isBlank()) {
            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("claimType"), filter.getClaimType())
            );
        }

        if(filter.getMinAmount() != null) {
            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), filter.getMinAmount())
            );
        }

        if(filter.getMaxAmount() != null) {
            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.lessThanOrEqualTo(root.get("amount"), filter.getMaxAmount())
            );
        }

        if(filter.getFromDate() != null) {
            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.greaterThanOrEqualTo(root.get("incidentDate"), filter.getFromDate())
            );
        }

        if(filter.getToDate() != null) {
            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.lessThanOrEqualTo(root.get("incidentDate"), filter.getToDate())
            );
        }

        String sortField =
                switch (filter.getSortBy()) {

                    case "amount" -> "amount";
                    case "incidentDate" -> "incidentDate";
                    case "status" -> "status";

                    default -> "createdAt";
                };

        Sort sort =
                filter.getSortDirection().equalsIgnoreCase("asc")
                        ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);

        Page<Claim> claims = claimRepository.findAll(specification,pageable);

        HashMap<String, Object> responseData = new HashMap<>();

        responseData.put("claims", claims.getContent());
        responseData.put("currentPage", claims.getNumber());
        responseData.put("totalItems", claims.getTotalElements());
        responseData.put("totalPages", claims.getTotalPages());
        responseData.put("last", claims.isLast());

        ApiResponse response = ApiResponse
                .builder()
                .success(true)
                .status(200)
                .message("Claims fetched successfully")
                .data(responseData)
                .build();

        return ResponseEntity.ok(response);
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

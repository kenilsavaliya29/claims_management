package com.claims.claim.repository;

import com.claims.claim.entity.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long>, JpaSpecificationExecutor<Claim> {
    public Page<Claim> findByCreatedBy(String createdBy, Pageable pageable);
    public Optional<Claim> findByClaimId(String claimId);
}

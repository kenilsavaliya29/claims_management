package com.claims.document.repository;

import com.claims.document.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentEntity,Long> {
    List<DocumentEntity> findByClaimId(String claimId);
}

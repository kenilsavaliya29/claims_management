package com.claims.document.repository;

import com.claims.document.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    List<DocumentEntity> findByClaimId(String claimId);

    Optional<DocumentEntity> findByDocumentId(String documentId);
}
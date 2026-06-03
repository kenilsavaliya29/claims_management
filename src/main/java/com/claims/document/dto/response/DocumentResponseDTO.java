package com.claims.document.dto.response;

import com.claims.document.DocumentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DocumentResponseDTO {

    private String claimId;

    private String documentId;

    private DocumentType documentType;

    private String originalFileName;

    private String contentType;

    private Long fileSize;

    private LocalDateTime uploadedAt;
}
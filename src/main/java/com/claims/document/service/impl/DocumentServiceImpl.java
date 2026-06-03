package com.claims.document.service.impl;

import com.claims.claim.entity.Claim;
import com.claims.claim.repository.ClaimRepository;
import com.claims.common.dto.ApiResponse;
import com.claims.common.exception.DocumentException;
import com.claims.document.DocumentType;
import com.claims.document.dto.response.DocumentResponseDTO;
import com.claims.document.entity.DocumentEntity;
import com.claims.document.repository.DocumentRepository;
import com.claims.document.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final ClaimRepository claimRepository;
    private final DocumentRepository documentRepository;

    public DocumentServiceImpl(ClaimRepository claimRepository, DocumentRepository documentRepository) {
        this.claimRepository = claimRepository;
        this.documentRepository = documentRepository;
    }


    @Override
    public ResponseEntity<ApiResponse> uploadDoc(String claimId, MultipartFile file, DocumentType documentType) {

        String currentUser = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        String uniqueDocumentId = "DOC-"+ UUID.randomUUID().toString().substring(0, 8);
        String fileType = file.getContentType();
        String originalFileName = file.getOriginalFilename();

        Claim claim = claimRepository.findByClaimIdAndCreatedBy(claimId, currentUser);

        if(claim == null) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .success(false)
                    .status(400)
                    .message("Claim Not Found")
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }

        // file empty check
        if(file.isEmpty()) {
            throw new DocumentException("File is empty");
        }
        int lastDot = originalFileName.lastIndexOf('.');

        String extension = originalFileName.substring(lastDot + 1);

        String storedFileName = UUID.randomUUID() + "." + extension;

        String uploadDirectory = "storage/claims/" + claimId;

        // file type check
        if(!Objects.equals(fileType, "application/pdf")
                && !Objects.equals(fileType, "image/png")
                && !Objects.equals(fileType, "image/jpeg")) {
            throw new IllegalArgumentException("Invalid file type");
        }


        try {
            Path uploadPath = Path.of(uploadDirectory).toAbsolutePath();

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path targetLocation = uploadPath.resolve(storedFileName);

            System.out.println(targetLocation);

            Files.copy(
                    file.getInputStream(),
                    targetLocation
            );

            DocumentEntity documentEntity = DocumentEntity.builder()
                    .documentId(uniqueDocumentId)
                    .claimId(claimId)
                    .uploadedBy(currentUser)
                    .documentType(documentType)
                    .originalFileName(originalFileName)
                    .storedFileName(storedFileName)
                    .filePath(targetLocation.toString())
                    .contentType(fileType)
                    .fileSize(file.getSize())
                    .uploadedAt(LocalDateTime.now())
                    .build();

            documentRepository.save(documentEntity);

            DocumentResponseDTO responseDTO = DocumentResponseDTO.builder()
                    .documentId(uniqueDocumentId)
                    .claimId(claimId)
                    .documentType(documentType)
                    .originalFileName(originalFileName)
                    .contentType(fileType)
                    .fileSize(file.getSize())
                    .uploadedAt(documentEntity.getUploadedAt())
                    .build();

            ApiResponse apiResponse = ApiResponse.builder()
                    .success(true)
                    .status(200)
                    .message("Document uploaded successfully")
                    .data(responseDTO)
                    .build();

            return ResponseEntity.ok(apiResponse);

        } catch (IOException e) {
            throw new DocumentException("Failed to store document");
        }

    }
}

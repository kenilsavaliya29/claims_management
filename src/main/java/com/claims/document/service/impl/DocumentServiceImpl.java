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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final ClaimRepository claimRepository;
    private final DocumentRepository documentRepository;

    protected final String basePath = "storage/claims/";

    public DocumentServiceImpl(ClaimRepository claimRepository, DocumentRepository documentRepository) {
        this.claimRepository = claimRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> fetchAllDocumentsMetaData(String claimId) {

        String currentUser = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        List<DocumentEntity> documentDetails = documentRepository.findByClaimIdAndUploadedBy(claimId, currentUser);

        if(documentDetails.isEmpty()) {
            ApiResponse errorResponse = ApiResponse.builder()
                    .success(false)
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("No documents found")
                    .build();
            return ResponseEntity.ok(errorResponse);
        }

        List<DocumentResponseDTO> response = new ArrayList<>();

        for(DocumentEntity documentEntity : documentDetails) {
            DocumentResponseDTO documentResponseDTO = DocumentResponseDTO
                    .builder()
                    .documentId(documentEntity.getDocumentId())
                    .documentType(documentEntity.getDocumentType())
                    .originalFileName(documentEntity.getOriginalFileName())
                    .contentType(documentEntity.getContentType())
                    .fileSize(documentEntity.getFileSize())
                    .uploadedAt(documentEntity.getUploadedAt())
                    .build();

            response.add(documentResponseDTO);
        }



        ApiResponse apiResponse = ApiResponse.builder()
                .success(true)
                .status(200)
                .message("Claim Document Metadata Fetched Successfully")
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<Resource> downloadDocument(String documentId) {

        String currentUser = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        DocumentEntity documentDetails = documentRepository.findByDocumentId(documentId).orElseThrow(() -> new DocumentException("Document not found"));

        if (!documentDetails.getUploadedBy().equals(currentUser)) {
            throw new DocumentException("Unauthorized access to document");
        }

        try {

            Path filePath = Paths
                    .get(documentDetails.getFilePath())
                    .toAbsolutePath();

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new DocumentException("Physical file not found");
            }

            return ResponseEntity.ok()
                    .contentType(
                            MediaType.parseMediaType(
                                    documentDetails.getContentType()
                            )
                    )
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" +
                                    documentDetails.getOriginalFileName() +
                                    "\""
                    )
                    .body(resource);

        } catch (MalformedURLException e) {
            throw new DocumentException(
                    "Unable to load document"
            );
        }
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

        String uploadDirectory = basePath + claimId;

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

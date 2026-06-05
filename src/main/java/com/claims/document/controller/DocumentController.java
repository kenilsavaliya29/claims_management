package com.claims.document.controller;

import com.claims.common.dto.ApiResponse;
import com.claims.document.DocumentType;
import com.claims.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/claims/{claimId}/documents")
    public ResponseEntity<ApiResponse> fetchAllDocuments(@PathVariable String claimId) {
        return documentService.fetchAllDocumentsMetaData(claimId);
    }

    // download document
    @GetMapping("/documents/{documentId}")
    public ResponseEntity<Resource> fetchDocument(@PathVariable String documentId) {
        return documentService.downloadDocument(documentId);
    }

    @PostMapping("/claims/{claimId}/documents")
    public ResponseEntity<ApiResponse> uploadDocument(
            @PathVariable String claimId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") DocumentType documentType
    ) {
        return documentService.uploadDoc(claimId, file, documentType);
    }
}

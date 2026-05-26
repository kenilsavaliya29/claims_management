package com.claims.document.controller;

import com.claims.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class DocumentUploadController {

    // fetch All document as per user logged in
    @GetMapping("/claims/{claimId}/documents")
    public ResponseEntity<ApiResponse> fetchAllDocuments(@PathVariable String claimId) {
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true).data(null).build());
    }

    // Download / View document
    @GetMapping("/documents/{documentId}")

    // upload document
    @PostMapping("/claims/{claimId}/documents")
    public ResponseEntity<ApiResponse> uploadDocument(
            @PathVariable String claimId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") String documentType
    ) {
        return ResponseEntity.ok(ApiResponse.builder()
                .success(true).data(null).build());
    }
}

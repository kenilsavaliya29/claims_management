package com.claims.document.service;

import com.claims.common.dto.ApiResponse;
import com.claims.document.DocumentType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    ResponseEntity<ApiResponse> uploadDoc(String claimId, MultipartFile file, DocumentType documentType);

//    ResponseEntity<ApiResponse> fetchAllDocuments(String claimId);
//
//    ResponseEntity<ApiResponse> fetchDocument(String documentId);
}
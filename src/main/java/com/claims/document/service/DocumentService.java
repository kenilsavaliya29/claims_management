package com.claims.document.service;

import com.claims.common.dto.ApiResponse;
import com.claims.document.DocumentType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    ResponseEntity<ApiResponse> uploadDoc(String claimId, MultipartFile file, DocumentType documentType);

    ResponseEntity<ApiResponse> fetchAllDocumentsMetaData(String claimId);

    ResponseEntity<Resource> downloadDocument(String documentId);

    ResponseEntity<ApiResponse> deleteDoc(String documentId);

}
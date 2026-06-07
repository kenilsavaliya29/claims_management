package com.claims.s3.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {

    void uploadFile(MultipartFile file, String s3key) throws IOException;
    byte[] downloadFile(String s3key);
    String deleteFile(String s3key);

}

package com.claims.s3.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {

    public void uploadFile(MultipartFile file, String s3key) throws IOException;
    public byte[] downloadFile(String s3key);
    public String deleteFile(String s3key);

}

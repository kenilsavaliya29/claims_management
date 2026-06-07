package com.claims.s3.service.impl;

import com.claims.properties.AwsProperties;
import com.claims.s3.service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

@Service
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    private final AwsProperties awsProperties;

    public S3ServiceImpl(S3Client s3Client,AwsProperties awsProperties) {
        this.s3Client = s3Client;
        this.awsProperties = awsProperties;
    }

    @Override
    public void uploadFile(MultipartFile file, String s3key) throws IOException {

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(awsProperties.getS3().getBucketName())
                .key(s3key)
                .build();

        s3Client.putObject(
                putObjectRequest,
                RequestBody.fromBytes(file.getBytes())
        );

    }

    @Override
    public byte[] downloadFile(String s3key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(awsProperties.getS3().getBucketName())
                .key(s3key)
                .build();

        ResponseBytes<GetObjectResponse> response =  s3Client.getObjectAsBytes(getObjectRequest);

        return response.asByteArray();
    }

    @Override
    public String deleteFile(String s3key) {

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(awsProperties.getS3().getBucketName())
                .key(s3key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);

        return "File Deleted Successfully";
    }

}

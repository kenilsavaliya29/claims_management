package com.claims.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    private String accessKey;
    private String secretKey;
    private String region;

    private S3 s3 = new S3();

    @Data
    public static class S3 {
        private String bucketName;
    }
}

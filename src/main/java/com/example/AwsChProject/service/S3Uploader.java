package com.example.AwsChProject.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class S3Uploader {

    @Value("${aws.access.key.id}")
    private String awsAccessKeyId;

    @Value("${aws.secret.access.key}")
    private String awsSecretAccessKey;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    public String uploadFile(MultipartFile file) throws IOException {
        // AWS 자격 증명 객체 생성
        AWSCredentials credentials = new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey);

        // S3 클라이언트 생성
        AmazonS3 s3Client = AmazonS3Client.builder()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        // 파일을 로컬에 임시로 저장
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path tempFilePath = Files.createTempFile(Paths.get(System.getProperty("java.io.tmpdir")), "", fileName);
        file.transferTo(tempFilePath.toFile());

        // 파일을 S3에 업로드
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, tempFilePath.toFile());
        s3Client.putObject(request);

        // 업로드 후 URL 반환
        String fileUrl = s3Client.getUrl(bucketName, fileName).toString();

        // 임시 파일 삭제
        Files.deleteIfExists(tempFilePath);

        return fileUrl;
    }
}

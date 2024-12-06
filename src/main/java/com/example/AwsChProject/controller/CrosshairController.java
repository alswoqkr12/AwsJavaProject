package com.example.AwsChProject.controller;

import com.example.AwsChProject.exception.ResourceNotFoundException;
import com.example.AwsChProject.model.Crosshair;
import com.example.AwsChProject.repository.CrosshairRepository;
import com.example.AwsChProject.service.S3Uploader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/crosshairs")
public class CrosshairController {


    @Autowired
    private CrosshairRepository crosshairRepository;

    @Autowired
    private S3Uploader s3Uploader;  // S3Uploader 서비스 주입

    // Crosshair 업로드 (이미지 파일을 S3에 업로드하고 RDS에 URL 저장)
    @PostMapping
    public Crosshair uploadCrosshair(@RequestParam("name") String name,
                                     @RequestParam("description") String description,
                                     @RequestParam("code") String code,
                                     @RequestParam("image") MultipartFile image) {
        try {
            // 이미지 파일을 S3에 업로드하고 URL을 반환
            String imageUrl = s3Uploader.uploadFile(image);  // S3Uploader에서 URL을 받아옴

            // Crosshair 객체를 생성하고, S3 URL을 DB에 저장
            Crosshair crosshair = new Crosshair(name, description, code, imageUrl);
            return crosshairRepository.save(crosshair);
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
        }
    }


}

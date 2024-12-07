package com.example.AwsChProject.controller;

import com.example.AwsChProject.model.Crosshair;
import com.example.AwsChProject.repository.CrosshairRepository;
import com.example.AwsChProject.service.S3Service;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/crosshairs")
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성
public class CrosshairController {

    private CrosshairRepository crosshairRepository; // final 필드
    private S3Service s3Service; // final 필드

    // Crosshair 업로드 (이미지 파일을 S3에 업로드하고 RDS에 URL 저장)
    @PostMapping
    public Crosshair uploadCrosshair(@RequestParam("name") String name,
                                     @RequestParam("description") String description,
                                     @RequestParam("code") String code,
                                     @RequestParam("image") MultipartFile image) { // MultipartFile 사용
        try {
            // S3에 이미지 파일 업로드
            String imageUrl = s3Service.uploadImage(image); // S3Service를 사용해 이미지 URL을 얻음

            // Crosshair 객체 생성, S3 URL을 DB에 저장
            Crosshair crosshair = new Crosshair(name, description, code, imageUrl);
            return crosshairRepository.save(crosshair);
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
        }
    }
}

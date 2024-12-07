package com.example.AwsChProject.controller;

import com.example.AwsChProject.model.Crosshair;
import com.example.AwsChProject.repository.CrosshairRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/crosshairs")
public class CrosshairController {

	 
	 
	 @Autowired
	    private CrosshairRepository crosshairRepository;

	    // 파일 저장 디렉토리 경로를 application.properties에서 가져오기
	    @Value("${file.upload-dir}")
	    private String uploadDir;

	    @PostMapping
	    public Crosshair uploadCrosshair(@RequestParam("name") String name,
	                                     @RequestParam("description") String description,
	                                     @RequestParam("code") String code,
	                                     @RequestParam("image") MultipartFile image) {
	        try {
	            // 이미지 파일 저장
	            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename(); // 파일명 고유화
	            Path filePath = Paths.get(uploadDir, fileName);
	            Files.copy(image.getInputStream(), filePath);

	            // 이미지 URL 생성 (로컬 서버 기준 경로)
	            String imageUrl = fileName;

	            // Crosshair 객체 저장
	            Crosshair crosshair = new Crosshair(name, description, code, imageUrl);
	            return crosshairRepository.save(crosshair);
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
        }
    }


}

package com.example.AwsChProject.controller;

import com.example.AwsChProject.exception.ResourceNotFoundException;
import com.example.AwsChProject.model.Crosshair;
import com.example.AwsChProject.repository.CrosshairRepository;
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


@CrossOrigin(origins = "http://107.20.189.174:3000")
@RestController
@RequestMapping("/api/crosshairs")
public class CrosshairController {

	@Autowired
	private CrosshairRepository crosshairRepository;

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Transactional
	@PostMapping
	public Crosshair uploadCrosshair(@RequestParam("name") String name, @RequestParam("description") String description,
			@RequestParam("code") String code, @RequestParam("image") MultipartFile image) {
		try {
			// 파일 이름을 현재 시간 기반으로 설정하여 충돌을 방지
			String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
			Path filePath = Paths.get(uploadDir, fileName);

			// 디렉토리가 없으면 생성
			Files.createDirectories(filePath.getParent());

			// 이미지 파일을 서버에 저장
			image.transferTo(filePath.toFile());

			// 이미지 경로를 DB에 저장할 수 있도록 Crosshair 객체 생성
			Crosshair crosshair = new Crosshair(name, description, code, "/images/" + fileName); // DB에는 파일 이름만 저장
			return crosshairRepository.save(crosshair);
		} catch (IOException e) {
			throw new RuntimeException("파일 저장 실패: " + e.getMessage());
		}
	}

	


}

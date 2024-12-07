package com.example.AwsChProject.controller;

import com.example.AwsChProject.model.Crosshair;
import com.example.AwsChProject.repository.CrosshairRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crosshairs")
public class CrosshairController {


    @Autowired
    private CrosshairRepository crosshairRepository;


    // Crosshair 업로드 (이미지 파일을 S3에 업로드하고 RDS에 URL 저장)
    @PostMapping
    public Crosshair uploadCrosshair(@RequestParam("name") String name,
                                     @RequestParam("description") String description,
                                     @RequestParam("code") String code,
                                     @RequestParam("image") String image) {
        try {

            // Crosshair 객체를 생성하고, S3 URL을 DB에 저장
            Crosshair crosshair = new Crosshair(name, description, code, image);
            return crosshairRepository.save(crosshair);
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
        }
    }


}

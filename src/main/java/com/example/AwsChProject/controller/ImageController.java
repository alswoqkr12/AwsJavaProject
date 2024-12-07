package com.example.AwsChProject.controller;




import com.example.AwsChProject.model.Crosshair;
import com.example.AwsChProject.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://107.20.189.174:3000")
@RestController
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/api/images")
    public List<Crosshair> getImages() {
        return imageRepository.findAll();  // DB에서 이미지 정보를 가져옴
    }
}


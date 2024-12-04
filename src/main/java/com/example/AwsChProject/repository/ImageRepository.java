package com.example.AwsChProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.AwsChProject.model.Crosshair;

public interface ImageRepository extends JpaRepository<Crosshair, Long> {
}

package com.example.AwsChProject.model;

import jakarta.persistence.*;

@Entity
public class Crosshair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    private String name;
    private String code;
    private String description;
    private String imageUrl;
    
    public Crosshair() {
        // 기본 생성자는 Hibernate에서 객체를 인스턴스화할 때 필요
    }



    // 생성자
    public Crosshair(String name, String code, String description, String imageUrl) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getter와 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
    
   
}

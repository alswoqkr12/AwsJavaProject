package com.example.AwsChProject.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // 임시 사용자 정보
    private static final String DUMMY_USERNAME = "admin";
    private static final String DUMMY_PASSWORD = "admin";

    public boolean authenticate(String username, String password) {
        // 아이디와 비밀번호가 일치하면 인증 성공
        return DUMMY_USERNAME.equals(username) && DUMMY_PASSWORD.equals(password);
    }
}


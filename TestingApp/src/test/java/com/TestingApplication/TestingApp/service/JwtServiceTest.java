package com.TestingApplication.TestingApp.service;

import com.TestingApplication.TestingApp.entity.User;
import org.hibernate.dialect.sql.ast.SybaseSqmToSqlAstConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void contextLoads() {
        User user = new User(4L, "email@gmail.com", "password");
        String token = jwtService.generateToken(user);

        System.out.println(token);

        Long subject = jwtService.validateToken(token);

        System.out.println(subject);
    }
}
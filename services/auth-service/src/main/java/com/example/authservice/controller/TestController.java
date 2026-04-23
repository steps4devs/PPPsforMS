package com.example.authservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class TestController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/test")
    public String test() {
        return "¡Conexión exitosa a Auth Service! El microservicio está vivo y enrutado. Instancia en el puerto: " + serverPort;
    }
}

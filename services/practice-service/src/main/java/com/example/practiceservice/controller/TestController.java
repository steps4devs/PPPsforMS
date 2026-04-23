package com.example.practiceservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/practice")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "¡Conexión exitosa a Practice Service! Listo para recibir lógicas de prácticas.";
    }
}

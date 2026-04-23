package com.example.profileservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @GetMapping("/test")
    public String test() {
        return "¡Conexión exitosa a Profile Service! Listo para recibir perfiles de estudiantes y tutores.";
    }
}

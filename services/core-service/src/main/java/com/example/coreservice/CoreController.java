package com.example.coreservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/core")
public class CoreController {

    @GetMapping("/test")
    public String test() {
        return "¡Conexión exitosa a Core Service! Listo para recibir gestión de empresas y periodos.";
    }
}

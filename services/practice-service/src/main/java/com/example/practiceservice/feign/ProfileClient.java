package com.example.practiceservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile-service")
public interface ProfileClient {
    
    @GetMapping("/api/v1/profile/students/{id}")
    Object getStudentById(@PathVariable("id") Long id);
}

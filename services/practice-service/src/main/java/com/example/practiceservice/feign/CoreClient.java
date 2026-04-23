package com.example.practiceservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "core-service")
public interface CoreClient {
    
    @GetMapping("/api/v1/core/companies/{id}")
    Object getCompanyById(@PathVariable("id") Long id);
}

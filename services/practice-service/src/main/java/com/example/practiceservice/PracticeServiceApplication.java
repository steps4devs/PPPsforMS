package com.example.practiceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PracticeServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PracticeServiceApplication.class, args);
	}
}

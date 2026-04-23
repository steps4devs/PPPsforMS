package com.example.profileservice.controller;

import com.example.profileservice.entity.StudentProfile;
import com.example.profileservice.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile/students")
@RequiredArgsConstructor
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    @GetMapping
    public ResponseEntity<List<StudentProfile>> getAll() {
        return ResponseEntity.ok(studentProfileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentProfile> getById(@PathVariable Long id) {
        StudentProfile profile = studentProfileService.findById(id);
        return profile != null ? ResponseEntity.ok(profile) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<StudentProfile> getByUserId(@PathVariable Long userId) {
        StudentProfile profile = studentProfileService.findByUserId(userId);
        return profile != null ? ResponseEntity.ok(profile) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<StudentProfile> create(@RequestBody StudentProfile studentProfile) {
        return ResponseEntity.ok(studentProfileService.save(studentProfile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentProfile> update(@PathVariable Long id, @RequestBody StudentProfile studentProfile) {
        StudentProfile updated = studentProfileService.update(id, studentProfile);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

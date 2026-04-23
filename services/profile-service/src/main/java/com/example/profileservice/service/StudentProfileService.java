package com.example.profileservice.service;

import com.example.profileservice.entity.StudentProfile;
import com.example.profileservice.repository.StudentProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;

    @Transactional(readOnly = true)
    public List<StudentProfile> findAll() {
        return studentProfileRepository.findAll();
    }

    @Transactional(readOnly = true)
    public StudentProfile findById(Long id) {
        return studentProfileRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public StudentProfile findByUserId(Long userId) {
        return studentProfileRepository.findByUserId(userId).orElse(null);
    }

    @Transactional
    public StudentProfile save(StudentProfile studentProfile) {
        return studentProfileRepository.save(studentProfile);
    }

    @Transactional
    public StudentProfile update(Long id, StudentProfile details) {
        StudentProfile profile = findById(id);
        if (profile != null) {
            profile.setCode(details.getCode());
            profile.setPhone(details.getPhone());
            profile.setCareerName(details.getCareerName());
            profile.setSemester(details.getSemester());
            // userId remains immutable
            return studentProfileRepository.save(profile);
        }
        return null;
    }

    @Transactional
    public void delete(Long id) {
        studentProfileRepository.deleteById(id);
    }
}

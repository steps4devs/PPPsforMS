package com.example.practiceservice.service;

import com.example.practiceservice.dto.PlanResponseDto;
import com.example.practiceservice.entity.Plan;
import com.example.practiceservice.feign.CoreClient;
import com.example.practiceservice.feign.ProfileClient;
import com.example.practiceservice.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final ProfileClient profileClient;
    private final CoreClient coreClient;

    @Transactional(readOnly = true)
    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PlanResponseDto findByIdWithDetails(Long id) {
        Plan plan = planRepository.findById(id).orElse(null);
        if (plan == null) {
            return null;
        }

        Object student = null;
        Object company = null;

        try {
            student = profileClient.getStudentById(plan.getStudentId());
        } catch (Exception e) {
            // Log error
        }

        try {
            company = coreClient.getCompanyById(plan.getCompanyId());
        } catch (Exception e) {
            // Log error
        }

        return PlanResponseDto.builder()
                .plan(plan)
                .student(student)
                .company(company)
                .build();
    }

    @Transactional
    public Plan save(Plan plan) {
        return planRepository.save(plan);
    }

    @Transactional
    public void delete(Long id) {
        planRepository.deleteById(id);
    }
}

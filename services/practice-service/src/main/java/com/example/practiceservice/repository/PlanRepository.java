package com.example.practiceservice.repository;

import com.example.practiceservice.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByStudentId(Long studentId);
    List<Plan> findByCompanyId(Long companyId);
}

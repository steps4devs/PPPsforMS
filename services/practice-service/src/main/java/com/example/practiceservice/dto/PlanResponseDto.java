package com.example.practiceservice.dto;

import com.example.practiceservice.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanResponseDto {
    private Plan plan;
    private Object student;
    private Object company;
}

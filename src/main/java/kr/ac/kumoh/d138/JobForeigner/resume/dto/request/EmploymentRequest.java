package kr.ac.kumoh.d138.JobForeigner.resume.dto.request;


import kr.ac.kumoh.d138.JobForeigner.resume.domain.Employment;

import java.time.LocalDateTime;

public record EmploymentRequest (
        String companyName,
        String departmentName,
        String jobTitle,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String achievement
){
    public Employment toEmployment(){
        return Employment.builder()
                .companyName(companyName)
                .departmentName(departmentName)
                .jobTitle(jobTitle)
                .startDate(startDate)
                .endDate(endDate)
                .achievement(achievement)
                .build();
    }
}

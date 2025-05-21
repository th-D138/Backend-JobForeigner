package kr.ac.kumoh.d138.JobForeigner.resume.dto.response;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Employment;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;

import java.time.LocalDateTime;
import java.util.List;

public record EmploymentResponse(
        String companyName,
        String departmentName,
        String jobTitle,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String achievement
){
    public static EmploymentResponse toEmploymentResponse(Employment employment) {
        return new EmploymentResponse(
                employment.getCompanyName(),
                employment.getDepartmentName(),
                employment.getJobTitle(),
                employment.getStartDate(),
                employment.getEndDate(),
                employment.getAchievement()
        );
    }

    public static List<EmploymentResponse> toEmploymentResponseList(Resume resume) {
        return resume.getEmployments().stream()
                .map(EmploymentResponse::toEmploymentResponse)
                .toList();
    }
}

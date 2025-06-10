package kr.ac.kumoh.d138.JobForeigner.resume.dto.request;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Degree;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Education;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.GraduationStatus;

import java.time.LocalDate;

public record EducationRequest(
        String educationName,
        String major,
        LocalDate yearOfGraduation,
        Degree degree,
        GraduationStatus graduationStatus,
        String etc
) {
    public Education toEducation(){
        return Education.builder()
                .educationName(educationName)
                .major(major)
                .yearOfGraduation(yearOfGraduation)
                .degree(degree)
                .graduationStatus(graduationStatus)
                .etc(etc)
                .build();
    }
}

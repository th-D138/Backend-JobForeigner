package kr.ac.kumoh.d138.JobForeigner.resume.dto.response;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Degree;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Education;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.GraduationStatus;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;

import java.time.LocalDateTime;
import java.util.List;

public record EducationResponse(
        String educationName,
        String major,
        LocalDateTime yearOfGraduation,
        Degree degree,
        GraduationStatus graduationStatus,
        String etc
) {
    public static EducationResponse toEducationResponse(Education education) {
        return new EducationResponse(
                education.getEducationName(),
                education.getMajor(),
                education.getYearOfGraduation(),
                education.getDegree(),
                education.getGraduationStatus(),
                education.getEtc()
        );
    }

    public static List<EducationResponse> toEducationResponseList(Resume resume) {
        return resume.getEducations().stream()
                .map(EducationResponse::toEducationResponse)
                .toList();
    }
}

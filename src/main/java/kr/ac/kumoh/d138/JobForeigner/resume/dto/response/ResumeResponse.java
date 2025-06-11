package kr.ac.kumoh.d138.JobForeigner.resume.dto.response;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;

import java.time.LocalDateTime;
import java.util.List;

public record ResumeResponse(
        Long resumeId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<EducationResponse> educations,
        List<EmploymentResponse> employments,
        List<CertificatesResponse> certificates,
        List<AwardsResponse> awards,
        List<SkillsResponse> skills,
        List<LanguagesResponse> languages,
        List<PortfoliosResponse> portfolios,
        JobPreferenceResponse jobPreference,
        List<ExpatResponse> expats,
        String imageUrl
) {
    public static ResumeResponse toResumeResponse(Resume resume) {
        return new ResumeResponse(
                resume.getId(),
                resume.getCreatedAt(),
                resume.getUpdatedAt(),
                EducationResponse.toEducationResponseList(resume),
                EmploymentResponse.toEmploymentResponseList(resume),
                CertificatesResponse.toCertificatesResponseList(resume),
                AwardsResponse.toAwardsResponseList(resume),
                SkillsResponse.toSkillsResponseList(resume),
                LanguagesResponse.toLanguagesResponseList(resume),
                PortfoliosResponse.toPortfoliosResponseList(resume),
                JobPreferenceResponse.toJobPreferenceResponse(resume),
                ExpatResponse.toExpatResponseList(resume),
                resume.getMember().getProfileImageUrl()
        );
    }
}

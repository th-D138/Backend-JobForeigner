package kr.ac.kumoh.d138.JobForeigner.resume.dto.request;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.*;
import java.util.List;

public record ResumeRequest(
        List<EducationRequest> educations,
        List<EmploymentRequest> employments,
        List<CertificatesRequest> certificates,
        List<AwardsRequest> awards,
        List<SkillsRequest> skills,
        List<LanguagesRequest> languages,
        List<PortfoliosRequest> portfolios,
        JobPreferenceRequest jobPreference,
        List<ExpatRequest> expat
) {

}

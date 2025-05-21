package kr.ac.kumoh.d138.JobForeigner.resume.dto.response;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.JobPreference;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;

import java.util.List;

public record JobPreferenceResponse(
        String desiredJob,
        String desiredEmploymentType,
        Long desiredSalary,
        String desiredLocation
) {
    public static JobPreferenceResponse toJobPreferenceResponse(Resume resume) {
        return new JobPreferenceResponse(
                resume.getJobPreference().getDesiredJob(),
                resume.getJobPreference().getDesiredEmploymentType(),
                resume.getJobPreference().getDesiredSalary(),
                resume.getJobPreference().getDesiredLocation()
        );
    }
}

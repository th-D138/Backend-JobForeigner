package kr.ac.kumoh.d138.JobForeigner.resume.dto.request;


import kr.ac.kumoh.d138.JobForeigner.resume.domain.JobPreference;

public record JobPreferenceRequest(
        String desiredJob,
        String desiredEmploymentType,
        Long desiredSalary,
        String desiredLocation
) {
    public JobPreference toJobPreference() {
        return JobPreference.builder()
                .desiredJob(desiredJob)
                .desiredEmploymentType(desiredEmploymentType)
                .desiredSalary(desiredSalary)
                .desiredLocation(desiredLocation)
                .build();
    }
}

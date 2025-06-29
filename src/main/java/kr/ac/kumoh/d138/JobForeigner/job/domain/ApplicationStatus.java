package kr.ac.kumoh.d138.JobForeigner.job.domain;

public enum ApplicationStatus {
    APPLIED("지원함"),
    ACCEPTED("합격"),
    REJECTED("불합격"),
    CANCELLED("지원취소");

    private final String description;

    ApplicationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
package kr.ac.kumoh.d138.JobForeigner.job.dto.company.request;

import jakarta.validation.constraints.NotNull;
import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPostStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JobPostRequestDto {
    @NotNull
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String location;
    @NotNull
    private String employment_type;
    @NotNull
    private String salary;
    @NotNull
    private String career;
    @NotNull
    private JobPostStatus published;
    @NotNull
    private String grade;
    @NotNull
    private LocalDateTime expiryAt;
    @NotNull
    private Long companyId;
}

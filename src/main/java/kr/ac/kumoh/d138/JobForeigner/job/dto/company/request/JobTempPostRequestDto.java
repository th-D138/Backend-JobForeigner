package kr.ac.kumoh.d138.JobForeigner.job.dto.company.request;

import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPostStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JobTempPostRequestDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String employmentType;
    private String salary;
    private String career;
    private JobPostStatus published;
    private String grade;
    private LocalDateTime expiryAt;
    private Long companyId;
}

package kr.ac.kumoh.d138.JobForeigner.job.dto.response;

import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPost;
import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPostStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Setter
public class JobPostResponseDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String employment_type;
    private String salary;
    private String career;
    private JobPostStatus published;
    private String grade;
    private LocalDateTime expiryAt;

    @Builder
    public JobPostResponseDto(Long id, String title, String description, String location,
                              String employment_type, String salary, String career,
                              JobPostStatus published,LocalDateTime expiryAt, String grade) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.employment_type = employment_type;
        this.salary = salary;
        this.career = career;
        this.published = published;
        this.expiryAt = expiryAt;
        this.grade = grade;
    }

    public static JobPostResponseDto fromEntity(JobPost jobPost) {
        return JobPostResponseDto.builder()
                .id(jobPost.getId())
                .title(jobPost.getTitle())
                .description(jobPost.getDescription())
                .location(jobPost.getLocation())
                .employment_type(jobPost.getEmploymentType())
                .salary(jobPost.getSalary())
                .career(jobPost.getCareer())
                .published(jobPost.getPublished())
                .expiryAt(jobPost.getExpiryAt())
                .grade(jobPost.getGrade())
                .build();
    }
}
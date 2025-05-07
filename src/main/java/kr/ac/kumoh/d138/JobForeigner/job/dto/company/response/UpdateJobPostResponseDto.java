package kr.ac.kumoh.d138.JobForeigner.job.dto.company.response;

import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPost;
import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPostStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateJobPostResponseDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String employmentType;
    private String salary;
    private String career;
    private JobPostStatus published;
    private String grade;

    @Builder
    public UpdateJobPostResponseDto(Long id, String title, String description, String location, String employmentType, String salary, String career, JobPostStatus published, String grade) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.employmentType = employmentType;
        this.salary = salary;
        this.career = career;
        this.published = published;
        this.grade = grade;
    }

    public static UpdateJobPostResponseDto fromEntity(JobPost jobPost) {
        return UpdateJobPostResponseDto.builder()
                .id(jobPost.getId())
                .title(jobPost.getTitle())
                .description(jobPost.getDescription())
                .location(jobPost.getLocation())
                .employmentType(jobPost.getEmploymentType())
                .salary(jobPost.getSalary())
                .career(jobPost.getCareer())
                .published(jobPost.getPublished())
                .grade(jobPost.getGrade())
                .build();
    }
}

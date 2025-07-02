package kr.ac.kumoh.d138.JobForeigner.job.dto.response;

import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPost;
import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPostStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPostDetailResponseDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String employmentType;
    private String salary;
    private String career;
    private JobPostStatus published;
    private String grade;
    private Boolean isScrapped;

    @Builder
    public JobPostDetailResponseDto(Long id, String title, String description, String location,
                                    String employmentType, String salary, String career,
                                    JobPostStatus published, String grade, Boolean isScrapped) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.employmentType = employmentType;
        this.salary = salary;
        this.career = career;
        this.published = published;
        this.grade = grade;
        this.isScrapped = isScrapped;
    }

    public static JobPostDetailResponseDto fromEntity(JobPost jobPost, boolean isScrapped) {
        return JobPostDetailResponseDto.builder()
                .id(jobPost.getId())
                .title(jobPost.getTitle())
                .description(jobPost.getDescription())
                .location(jobPost.getLocation())
                .employmentType(jobPost.getEmploymentType())
                .salary(jobPost.getSalary())
                .career(jobPost.getCareer())
                .published(jobPost.getPublished())
                .grade(jobPost.getGrade())
                .isScrapped(isScrapped)
                .build();
    }
}
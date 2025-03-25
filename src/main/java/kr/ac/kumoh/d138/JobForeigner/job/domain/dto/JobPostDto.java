package kr.ac.kumoh.d138.JobForeigner.job.domain.dto;

import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPost;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JobPostDto {
    private Long jobPostId;
    private String title;
    private String location;
    private String employmentType;
    private String career;

    public static JobPostDto fromEntity(JobPost jobPost) {
        return JobPostDto.builder()
                .jobPostId(jobPost.getId())
                .title(jobPost.getTitle())
                .location(jobPost.getLocation())
                .career(jobPost.getCareer())
                .build();
    }
}

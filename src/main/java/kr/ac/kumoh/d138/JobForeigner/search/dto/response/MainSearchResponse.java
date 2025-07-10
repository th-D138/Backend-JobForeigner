package kr.ac.kumoh.d138.JobForeigner.search.dto.response;

import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPostStatus;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.JobPostResponseDto;

import java.util.List;

public record MainSearchResponse (
        Long companyId,
        String companyName,
        String companyDescription,
        String companyAddress,
        int employeeCount,
        List<JobPostResponseDto> jobPostList
){
}

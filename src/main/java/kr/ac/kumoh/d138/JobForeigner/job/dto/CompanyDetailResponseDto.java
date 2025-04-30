package kr.ac.kumoh.d138.JobForeigner.job.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CompanyDetailResponseDto {
    private CompanyInfoDto companyInfoDto;
    private JobPostDto jobPostDto;
    private SalaryInfoDto salaryInfoDto;
    private CompanyRatingDto companyRatingDto;
    private List<ReviewDto> reviewDto;
}

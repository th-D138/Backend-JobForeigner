package kr.ac.kumoh.d138.JobForeigner.job.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyResponseDto {
    private Long companyId;
    private String companyName;
    private String description;
    private String address;
    private int employeeCount;
}

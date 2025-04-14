package kr.ac.kumoh.d138.JobForeigner.job.dto;

import kr.ac.kumoh.d138.JobForeigner.job.domain.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyInfoDto {
    private Long companyId;
    private String companyName;
    private int employeeCount;
    private String address;
    private String category;
    private String url;
    private String ceoName;
    private String description;

    public static CompanyInfoDto fromEntity(Company company) {
        return CompanyInfoDto.builder()
                .companyId(company.getId())
                .companyName(company.getCompanyName())
                .employeeCount(company.getEmployeeCount())
                .address(company.getAddress())
                .category(company.getCategory())
                .url(company.getUrl())
                .ceoName(company.getCeoName())
                .description(company.getDescription())
                .build();
    }
}

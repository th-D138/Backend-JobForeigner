package kr.ac.kumoh.d138.JobForeigner.job.dto.response;

import kr.ac.kumoh.d138.JobForeigner.job.domain.Company;
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

    public static CompanyResponseDto fromEntity(Company company) {
        CompanyResponseDto companyResponseDto = new CompanyResponseDto();
        companyResponseDto.setCompanyId(company.getId());
        companyResponseDto.setCompanyName(company.getCompanyName());
        companyResponseDto.setDescription(company.getDescription());
        companyResponseDto.setAddress(company.getAddress());
        companyResponseDto.setEmployeeCount(company.getEmployeeCount());
        return companyResponseDto;
    }
}

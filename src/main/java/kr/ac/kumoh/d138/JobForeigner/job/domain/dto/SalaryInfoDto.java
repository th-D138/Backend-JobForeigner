package kr.ac.kumoh.d138.JobForeigner.job.domain.dto;

import kr.ac.kumoh.d138.JobForeigner.job.domain.Company;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SalaryInfoDto {
    private Integer averageSalary;
    private Integer monthlySalary;
    //TODO: 평균 입퇴사자 구현

    public static SalaryInfoDto fromEntity(Company company){
        return SalaryInfoDto.builder()
                .averageSalary(company.getAverageSalary())
                .monthlySalary(company.getMonthlyTakeHome())
                .build();
    }
}

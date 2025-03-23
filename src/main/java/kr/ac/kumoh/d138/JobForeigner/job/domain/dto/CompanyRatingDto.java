package kr.ac.kumoh.d138.JobForeigner.job.domain.dto;

import kr.ac.kumoh.d138.JobForeigner.job.domain.Company;
import kr.ac.kumoh.d138.JobForeigner.job.domain.CompanyRating;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompanyRatingDto {
    private Long companyId;
    private Float averageSalarySatisfaction;
    private Float averageWorkLifeBalance;
    private Float averageOrganizationalCulture;
    private Float averageWelfare;
    private Float averageJobStability;
    private Float averageRating;
    private Long totalReviews;

    public static CompanyRatingDto fromEntity(Company company) {
        CompanyRating cr=company.getCompanyRating();
        if(cr==null){
            return CompanyRatingDto.builder()
                    .companyId(company.getId())
                    .averageSalarySatisfaction(0f)
                    .averageWorkLifeBalance(0f)
                    .averageOrganizationalCulture(0f)
                    .averageWelfare(0f)
                    .averageJobStability(0f)
                    .averageRating(0f)
                    .totalReviews(0L)
                    .build();
        }
        return CompanyRatingDto.builder()
                .companyId(company.getId())
                .averageSalarySatisfaction(cr.getAverageSalarySatisfaction())
                .averageWorkLifeBalance(cr.getAverageWorkLifeBalance())
                .averageOrganizationalCulture(cr.getAverageOrganizationalCulture())
                .averageWelfare(cr.getAverageWelfare())
                .averageJobStability(cr.getAverageJobStability())
                .averageRating(company.getAverageRating())
                .totalReviews(cr.getTotalReviews())
                .build();
    }
}

package kr.ac.kumoh.d138.JobForeigner.job.domain.dto;

import kr.ac.kumoh.d138.JobForeigner.job.domain.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class CompanyRatingDto {
    private Long companyId;

    @Builder.Default
    private Float averageSalarySatisfaction = 0f;

    @Builder.Default
    private Float averageWorkLifeBalance = 0f;

    @Builder.Default
    private Float averageOrganizationalCulture = 0f;

    @Builder.Default
    private Float averageWelfare = 0f;

    @Builder.Default
    private Float averageJobStability = 0f;

    @Builder.Default
    private Float averageRating = 0f;

    @Builder.Default
    private Long totalReviews = 0L;

    @Builder
    public CompanyRatingDto(Long companyId,
                            Float averageSalarySatisfaction,
                            Float averageWorkLifeBalance,
                            Float averageOrganizationalCulture,
                            Float averageWelfare,
                            Float averageJobStability,
                            Float averageRating,
                            Long totalReviews) {
        this.companyId = companyId;
        this.averageSalarySatisfaction = averageSalarySatisfaction;
        this.averageWorkLifeBalance = averageWorkLifeBalance;
        this.averageOrganizationalCulture = averageOrganizationalCulture;
        this.averageWelfare = averageWelfare;
        this.averageJobStability = averageJobStability;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
    }

    public static CompanyRatingDto fromEntity(Company company) {
        return company.findCompanyRating()
                .map(cr -> CompanyRatingDto.builder()
                        .companyId(company.getId())
                        .averageSalarySatisfaction(cr.getAverageSalarySatisfaction())
                        .averageWorkLifeBalance(cr.getAverageWorkLifeBalance())
                        .averageOrganizationalCulture(cr.getAverageOrganizationalCulture())
                        .averageWelfare(cr.getAverageWelfare())
                        .averageJobStability(cr.getAverageJobStability())
                        .averageRating(company.getAverageRating())
                        .totalReviews(cr.getTotalReviews())
                        .build())
                .orElseGet(CompanyRatingDto::new);
    }
}

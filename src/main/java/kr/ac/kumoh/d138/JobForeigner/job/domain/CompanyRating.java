package kr.ac.kumoh.d138.JobForeigner.job.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CompanyRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 회사의 집계 정보인지
    @OneToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    // 각 항목별 평균
    @Column(name="average_salary_satisfaction", nullable = false)
    private Float averageSalarySatisfaction = 0.0f;

    @Column(name="average_work_life_balance", nullable = false)
    private Float averageWorkLifeBalance = 0.0f;

    @Column(name="average_organizational_culture", nullable = false)
    private Float averageOrganizationalCulture = 0.0f;

    @Column(name="average_welfare", nullable = false)
    private Float averageWelfare = 0.0f;

    @Column(name="average_job_stability", nullable = false)
    private Float averageJobStability = 0.0f;

    // 총 리뷰(평점) 개수
    @Column(name="total_reviews", nullable = false)
    private Long totalReviews = 0L;

    public void updateCompanyRating(float salary, float workLife, float culture, float welfare, float stability) {
        totalReviews++;
        averageSalarySatisfaction
                = (averageSalarySatisfaction * (totalReviews - 1) + salary) / totalReviews;
        averageWorkLifeBalance
                = (averageWorkLifeBalance * (totalReviews - 1) + workLife) / totalReviews;
        averageOrganizationalCulture
                = (averageOrganizationalCulture * (totalReviews - 1) + culture) / totalReviews;
        averageWelfare
                = (averageWelfare * (totalReviews - 1) + welfare) / totalReviews;
        averageJobStability
                = (averageJobStability * (totalReviews - 1) + stability) / totalReviews;
    }
}

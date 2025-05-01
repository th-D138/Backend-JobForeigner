package kr.ac.kumoh.d138.JobForeigner.job.dto;

import kr.ac.kumoh.d138.JobForeigner.rating.Rating;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDto {
    private Long ratingId;

    private Float salarySatisfaction;
    private String salaryComment;

    private Float workLifeBalance;
    private String workLifeComment;

    private Float organizationalCulture;
    private String cultureComment;

    private Float welfare;
    private String welfareComment;

    private Float jobStability;
    private String stabilityComment;

    private String reviewerName;

    //TODO 리뷰자 이름 나중에 토큰에서 꺼내서 쓰기
    public static ReviewDto fromEntity(Rating rating, String reviewerName) {
        return ReviewDto.builder()
                .ratingId(rating.getId())
                .salarySatisfaction(rating.getSalarySatisfaction())
                .salaryComment(rating.getSalaryComment())

                .workLifeBalance(rating.getWorkLifeBalance())
                .workLifeComment(rating.getWorkLifeComment())

                .organizationalCulture(rating.getOrganizationalCulture())
                .cultureComment(rating.getCultureComment())

                .welfare(rating.getWelfare())
                .welfareComment(rating.getWelfareComment())

                .jobStability(rating.getJobStability())
                .stabilityComment(rating.getStabilityComment())

                .reviewerName(reviewerName)
                .build();
    }
}

package kr.ac.kumoh.d138.JobForeigner.rating;

import jakarta.persistence.*;
import kr.ac.kumoh.d138.JobForeigner.global.response.AuditEntity;
import kr.ac.kumoh.d138.JobForeigner.job.domain.Company;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import lombok.Getter;
@Entity
@Getter
public class Rating extends AuditEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rating_id",nullable = false)
    private Long id;

    @Column(name="salary_satisfaction", nullable = false)
    private Float salarySatisfaction;

    @Column(name="salary_comment", columnDefinition = "TEXT")
    private String salaryComment;

    @Column(name="work_life_balance", nullable = false)
    private Float workLifeBalance;

    @Column(name="work_life_comment", columnDefinition = "TEXT")
    private String workLifeComment;

    @Column(name="organizational_culture", nullable = false)
    private Float organizationalCulture;

    @Column(name="culture_comment", columnDefinition = "TEXT")
    private String cultureComment;

    @Column(name="welfare", nullable = false)
    private Float welfare;

    @Column(name="welfare_comment", columnDefinition = "TEXT")
    private String welfareComment;

    @Column(name="job_stability", nullable = false)
    private Float jobStability;

    @Column(name="stability_comment", columnDefinition = "TEXT")
    private String stabilityComment;

    @Column(name = "approve", nullable = false)
    private boolean approve = false;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="company_id")
    private Company company;
}

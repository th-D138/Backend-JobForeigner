package kr.ac.kumoh.d138.JobForeigner.application.domain;

import jakarta.persistence.*;
import kr.ac.kumoh.d138.JobForeigner.global.base.BaseEntity;
import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPost;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "job_application",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"member_id", "job_post_id"}
        )
)
@NoArgsConstructor
@Getter
public class JobApplication extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

    @Builder
    public JobApplication(Member member, JobPost jobPost, String resumeUrl) {
        this.member = member;
        this.jobPost = jobPost;
    }
}
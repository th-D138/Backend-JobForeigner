package kr.ac.kumoh.d138.JobForeigner.job.domain;

import jakarta.persistence.*;
import kr.ac.kumoh.d138.JobForeigner.global.base.BaseEntity;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_applications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "resume_id")
    private Long resumeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status")
    private ApplicationStatus applicationStatus;

    @Builder
    public JobApplication(JobPost jobPost, Member member, Long resumeId) {
        this.jobPost = jobPost;
        this.member = member;
        this.resumeId = resumeId;
        this.applicationStatus = ApplicationStatus.APPLIED;
    }
}
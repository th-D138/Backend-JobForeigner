
package kr.ac.kumoh.d138.JobForeigner.job.domain;

import jakarta.persistence.*;
import kr.ac.kumoh.d138.JobForeigner.global.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "job_post_id")
    private Long jobPostId;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @Builder
    public Notification(Long memberId, String title, String message, Long jobPostId,
                        boolean isRead, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.title = title;
        this.message = message;
        this.jobPostId = jobPostId;
        this.isRead = isRead;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
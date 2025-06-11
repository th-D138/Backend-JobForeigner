package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "award_name")
    private String awardName;

    @Column(name = "organization")
    private String organization;

    @Column(name = "award_year")
    private LocalDate awardYear;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    public Award(String awardName, String organization, LocalDate awardYear, String details){
        this.awardName = awardName;
        this.organization = organization;
        this.awardYear = awardYear;
        this.details = details;
    }

    public void setResume(Resume resume){
        this.resume = resume;
    }
}

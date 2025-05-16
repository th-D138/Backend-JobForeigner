package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "LocalDateTime")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "experience", columnDefinition = "TEXT")
    private String experience;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    public Expat(String country, LocalDateTime startDate, LocalDateTime endDate, String experience){
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.experience = experience;
    }

    public void setResume(Resume resume){
        this.resume = resume;
    }
}

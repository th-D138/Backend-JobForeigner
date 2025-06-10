package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "portfolio_url")
    private String portfolioUrl;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    public Portfolio(String portfolioUrl){
        this.portfolioUrl = portfolioUrl;
    }

    public void setResume(Resume resume){
        this.resume = resume;
    }
}

package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "certificate_name")
    private String certificateName;

    @Column(name = "organization")
    private String organization;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    public Certificate(String certificateName, String organization, LocalDate date){
        this.certificateName = certificateName;
        this.organization = organization;
        this.date = date;
    }

    public void setResume(Resume resume){
        this.resume = resume;
    }
}

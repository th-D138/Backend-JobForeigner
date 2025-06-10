package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "languages")
    private String languages;

    @Column(name = "proficiency")
    private String proficiency;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    public Language(String languages, String proficiency){
        this.languages = languages;
        this.proficiency = proficiency;
    }

    public void setResume(Resume resume){
        this.resume = resume;
    }
}

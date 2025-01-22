package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "languages")
    private String languages;

    @Column(name = "proficiency")
    private String proficiency;
}

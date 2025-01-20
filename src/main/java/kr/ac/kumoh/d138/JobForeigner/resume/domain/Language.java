package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;

@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "languages")
    private String languages;

    @Column(name = "proficiency")
    private String proficiency;
}

package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Expat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "LocalDateTime")
    private LocalDateTime start_date;

    @Column(name = "end_date")
    private LocalDateTime end_date;

    @Lob
    @Column(name = "experience")
    private String experience;
}

package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "award_name")
    private String award_name;

    @Column(name = "organization")
    private String organization;

    @Column(name = "year")
    private LocalDateTime year;

    @Lob
    @Column(name = "details")
    private String details;


}

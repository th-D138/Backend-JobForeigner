package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "certificate_name")
    private String certificate_name;

    @Column(name = "organization")
    private String organization;

    @Column(name = "date")
    private LocalDateTime date;

}

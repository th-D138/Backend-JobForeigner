package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String company_name;

    @Column(name = "department_name")
    private String department_name;

    @Column(name = "job_title")
    private String job_title;

    @Column(name = "start_date")
    private LocalDateTime start_date;

    @Column(name = "end_date")
    private LocalDateTime end_date;

    @Lob
    @Column(name = "achievement")
    private String achievement;
}

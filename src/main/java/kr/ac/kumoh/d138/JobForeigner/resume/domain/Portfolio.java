package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;

@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "portfolio_url")
    private String portfolio_url;
}

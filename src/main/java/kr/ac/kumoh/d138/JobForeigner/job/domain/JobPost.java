package kr.ac.kumoh.d138.JobForeigner.job.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class JobPost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String employment_type;

    @Column(nullable = false)
    private String salary;

    @Column(nullable = false)
    private String career;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createAt;

    @Column(name="updated_at",nullable = false)
    private LocalDateTime updateAt;

    @Column(name="expiry_at",nullable = false)
    private LocalDateTime expiryAt;

    @Column(nullable = false)
    private boolean Published;

    @Column(nullable = false)
    private String grade;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}

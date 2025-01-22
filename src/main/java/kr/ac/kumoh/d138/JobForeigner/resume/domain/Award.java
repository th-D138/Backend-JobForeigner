package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;


}

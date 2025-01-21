package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "education_name")
    private String education_name;

    @Column(name = "major")
    private String major;

    @Column(name = "year_of_graduation")
    private LocalDateTime year_of_graduation;

    @Column(name = "degree")
    private Degree degree;

    @Column(name = "graduation_status")
    private GraduationStatus graduation_status;

    @Column(name = "etc", columnDefinition = "TEXT")
    private String etc;
}

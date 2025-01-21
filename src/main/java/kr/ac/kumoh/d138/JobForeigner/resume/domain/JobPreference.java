package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "desired_job")
    private String desired_job;

    @Column(name = "desired_employment_type")
    private String desired_employment_type;

    @Column(name = "desired_salary")
    private Long desired_salary;

    @Column(name = "desired_location")
    private String desired_location;
}

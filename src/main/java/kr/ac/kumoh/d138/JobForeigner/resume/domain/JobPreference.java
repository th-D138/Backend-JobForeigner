package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "desired_job")
    private String desiredJob;

    @Column(name = "desired_employment_type")
    private String desiredEmploymentType;

    @Column(name = "desired_salary")
    private Long desiredSalary;

    @Column(name = "desired_location")
    private String desiredLocation;

    @OneToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    public JobPreference(String desiredJob, String desiredEmploymentType, Long desiredSalary, String desiredLocation){
        this.desiredJob = desiredJob;
        this.desiredEmploymentType = desiredEmploymentType;
        this.desiredSalary = desiredSalary;
        this.desiredLocation = desiredLocation;
    }

    public void setResume(Resume resume){
        this.resume = resume;
    }
}

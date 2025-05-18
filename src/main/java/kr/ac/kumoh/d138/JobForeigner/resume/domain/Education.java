package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "education_name")
    private String educationName;

    @Column(name = "major")
    private String major;

    @Column(name = "year_of_graduation")
    private LocalDateTime yearOfGraduation;

    @Enumerated(EnumType.STRING)
    @Column(name = "degree")
    private Degree degree;

    @Enumerated(EnumType.STRING)
    @Column(name = "graduation_status")
    private GraduationStatus graduationStatus;

    @Column(name = "etc", columnDefinition = "TEXT")
    private String etc;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    public Education(String educationName, String major, LocalDateTime yearOfGraduation, Degree degree, GraduationStatus graduationStatus, String etc){
        this.educationName = educationName;
        this.major = major;
        this.yearOfGraduation = yearOfGraduation;
        this.degree = degree;
        this.graduationStatus = graduationStatus;
        this.etc = etc;
    }

    public void setResume(Resume resume){
        this.resume = resume;
    }
}

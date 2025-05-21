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
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "achievement", columnDefinition = "TEXT")
    private String achievement;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    public Employment(String companyName, String departmentName, String jobTitle, LocalDateTime startDate, LocalDateTime endDate, String achievement){
        this.companyName = companyName;
        this.departmentName = departmentName;
        this.jobTitle = jobTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.achievement = achievement;
    }

    public void setResume(Resume resume){
        this.resume = resume;
    }
}

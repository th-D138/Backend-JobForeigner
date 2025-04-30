package kr.ac.kumoh.d138.JobForeigner.job.domain;

import jakarta.persistence.*;
import kr.ac.kumoh.d138.JobForeigner.job.dto.company.request.JobPostRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class JobPost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String location;

    @Column(name = "employment_type")
    private String employmentType;

    private String salary;

    private String career;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updateAt;

    @Column(name="expiry_at")
    private LocalDateTime expiryAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "published")
    private JobPostStatus published;

    private String grade;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

@Builder
public JobPost(String title, String description, String location, String employmentType, 
               String salary, String career, JobPostStatus published, String grade, 
               LocalDateTime expiryAt, Company company) {
    this.title = title;
    this.description = description;
    this.location = location;
    this.employmentType = employmentType;
    this.salary = salary;
    this.career = career;
    this.published = published;
    this.grade = grade;
    this.expiryAt = expiryAt;
    this.company = company;
}

    public void updatePost(JobPostRequestDto dto){
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.location = dto.getLocation();
        this.employmentType = dto.getEmployment_type();
        this.salary = dto.getSalary();
        this.career = dto.getCareer();
        this.grade = dto.getGrade();
        this.expiryAt = dto.getExpiryAt();
    }
}
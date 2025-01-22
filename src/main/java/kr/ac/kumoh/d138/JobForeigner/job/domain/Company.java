package kr.ac.kumoh.d138.JobForeigner.job.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id",nullable = false)
    private Long id;

    @Column(name = "company_name",nullable = false)
    private String companyName;

    @Column(name = "business_number",nullable = false)
    private String businessNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(name="phone_number",nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "company")
    private List<JobPost> jobPostList=new ArrayList<>();

    public void addJobPost(JobPost jobPost) {
        this.jobPostList.add(jobPost);  // Company의 jobPostList에 추가
        jobPost.setCompany(this);      // JobPost의 company 필드 설정
    }

}

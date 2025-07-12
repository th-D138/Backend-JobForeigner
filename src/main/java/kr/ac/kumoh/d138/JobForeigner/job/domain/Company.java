package kr.ac.kumoh.d138.JobForeigner.job.domain;

import jakarta.persistence.*;
import kr.ac.kumoh.d138.JobForeigner.rating.Rating;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Column(name="employee_count")
    private int employeeCount;

    @Column(nullable=false)
    private String category;

    @Column(name="ceo")
    private String ceoName;

    @Column(name = "average_salary") // 평균 연봉 (단위: 만원)
    private Integer averageSalary;

    @Column(name = "monthly_take_home") // 월 실수령액 (단위: 원)
    private Integer monthlyTakeHome;

    @Enumerated(EnumType.STRING)
    private CompanyCategory companyCategory;

    private String welfare;

    //TODO: 프론트 개발 임시용 필드, 추후 이미지 구현시 수정해야함
    private String imageUrl;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private CompanyRating companyRating;

    // 전체 평균 평점 (Radar Chart 등을 표시할 때, 5개 항목의 평균으로 활용 가능)
    @Column(name="average_rating")
    private float averageRating;

    @OneToMany(mappedBy = "company")
    private List<JobPost> jobPostList=new ArrayList<>();

    @OneToMany(mappedBy="company")
    private List<Rating> ratings=new ArrayList<>();

    public void addJobPost(JobPost jobPost) {
        this.jobPostList.add(jobPost);  // Company의 jobPostList에 추가
        jobPost.setCompany(this);      // JobPost의 company 필드 설정
    }
    public Optional<CompanyRating> findCompanyRating() {
        return Optional.ofNullable(this.companyRating);
    }
}

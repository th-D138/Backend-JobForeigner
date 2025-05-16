package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.ac.kumoh.d138.JobForeigner.global.base.BaseEntity;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resume extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resume_image_url")
    private String resumeImageUrl;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employment> employments;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificate> certificates;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Award> awards;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Language> languages = new ArrayList<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Portfolio> portfolios = new ArrayList<>();

    @OneToOne(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private JobPreference jobPreference;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expat> expat = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Resume(
            @NotNull Member member,
            String resumeImageUrl,
            List<Education> educations,
            List<Employment> employments,
            List<Certificate> certificates,
            List<Award> awards,
            List<Skill> skills,
            List<Language> languages,
            List<Portfolio> portfolios,
            JobPreference jobPreference,
            List<Expat> expat
    ) {
        this.member = member;
        this.resumeImageUrl = resumeImageUrl;
        this.educations = educations != null ? educations : new ArrayList<>();
        this.employments = employments != null ? employments : new ArrayList<>();
        this.certificates = certificates != null ? certificates : new ArrayList<>();
        this.awards = awards != null ? awards : new ArrayList<>();
        this.skills = skills != null ? skills : new ArrayList<>();
        this.languages = languages != null ? languages : new ArrayList<>();
        this.portfolios = portfolios != null ? portfolios : new ArrayList<>();
        this.jobPreference = jobPreference;
        this.expat = expat != null ? expat : new ArrayList<>();
    }

    public void updateResume(String resumeImageUrl, List<Education> educations, List<Employment> employments,
                             List<Certificate> certificates, List<Award> awards, List<Skill> skills,
                             List<Language> languages, List<Portfolio> portfolios, JobPreference jobPreference, List<Expat> expats
    ) {
        this.resumeImageUrl = resumeImageUrl;
        this.educations = educations;
        this.employments = employments;
        this.certificates = certificates;
        this.awards = awards;
        this.skills = skills;
        this.languages = languages;
        this.portfolios = portfolios;
        this.jobPreference = jobPreference;
        this.expat = expats;
    }

    // 양방향 연관관계 설정을 위한 편의 메서드
    public void createResume(Resume resume
    ){
        resume.getEducations().forEach(education -> education.setResume(this));
        resume.getEmployments().forEach(employment -> employment.setResume(this));
        resume.getCertificates().forEach(certificate -> certificate.setResume(this));
        resume.getAwards().forEach(award -> award.setResume(this));
        resume.getSkills().forEach(skill -> skill.setResume(this));
        resume.getLanguages().forEach(language -> language.setResume(this));
        resume.getPortfolios().forEach(portfolio -> portfolio.setResume(this));
        resume.getJobPreference().setResume(this);
        resume.getExpat().forEach(expat -> expat.setResume(this));
    }
}

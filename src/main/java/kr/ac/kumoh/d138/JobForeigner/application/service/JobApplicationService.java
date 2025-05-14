package kr.ac.kumoh.d138.JobForeigner.application.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.kumoh.d138.JobForeigner.application.dto.ResumeResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.domain.QJobPost;
import kr.ac.kumoh.d138.JobForeigner.member.domain.QMember;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JPAQueryFactory queryFactory;

    // 사용자 이력서 조회
    // 사용자 이력서 조회
    public List<ResumeResponseDto> getResumeList(Long id) {
        // 사용자 아이디를 통해 이력서를 조회
        QResume resume = QResume.resume;
        QAward award = QAward.award;
        QCertificate certificate = QCertificate.certificate;
        QEducation education = QEducation.education;
        QEmployment employment = QEmployment.employment;
        QExpat expat = QExpat.expat;
        QJobPreference jobPreference = QJobPreference.jobPreference;
        QPortfolio portfolio = QPortfolio.portfolio;
        QLanguage language = QLanguage.language;
        QSkill skill = QSkill.skill;
        QMember member = QMember.member;
        QJobPost jobPost = QJobPost.jobPost;

        List<Resume> resumeList = queryFactory
                .selectFrom(resume)
                .leftJoin(resume.member, member)
                .leftJoin(resume.educations, education)
                .leftJoin(resume.employments, employment)
                .leftJoin(resume.certificates, certificate)
                .leftJoin(resume.awards, award)
                .leftJoin(resume.skills, skill)
                .leftJoin(resume.languages, language)
                .leftJoin(resume.portfolios, portfolio)
                .leftJoin(resume.jobPreference, jobPreference)
                .leftJoin(resume.expat, expat)
                .where(member.id.eq(id))
                .distinct() // 중복 결과 제거
                .fetch();
        return resumeList.stream()
                .map(r -> new ResumeResponseDto(/* 필요한 파라미터 */) )
                .collect(Collectors.toList());
    }
}

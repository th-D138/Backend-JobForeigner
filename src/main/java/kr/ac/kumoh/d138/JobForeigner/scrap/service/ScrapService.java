package kr.ac.kumoh.d138.JobForeigner.scrap.service;

import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPost;
import kr.ac.kumoh.d138.JobForeigner.job.repository.JobPostRepository;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.member.repository.MemberRepository;
import kr.ac.kumoh.d138.JobForeigner.scrap.domain.Scrap;
import kr.ac.kumoh.d138.JobForeigner.scrap.dto.ScrapResponse;
import kr.ac.kumoh.d138.JobForeigner.scrap.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScrapService {
    
    private final ScrapRepository scrapRepository;
    private final JobPostRepository jobPostRepository;
    private final MemberRepository memberRepository;
    

    @Transactional
    public ScrapResponse toggleScrap(Long memberId, Long jobPostId) {
        boolean already = scrapRepository.existsByMemberIdAndJobPostId(memberId, jobPostId);

        if (already) {
            // 삭제
            scrapRepository.deleteByMemberIdAndJobPostId(memberId, jobPostId);
            return new ScrapResponse(jobPostId, false, "스크랩 취소됨");
        }
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new BusinessException(ExceptionType.JOBPOST_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new BusinessException(ExceptionType.MEMBER_NOT_FOUND));
        Scrap scrap = Scrap.builder()
                .member(member)
                .jobPost(jobPost)
                .build();
        scrapRepository.save(scrap);
        return new ScrapResponse(jobPostId,true,"스크랩 추가됨");
    }
}
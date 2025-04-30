package kr.ac.kumoh.d138.JobForeigner.member.service;

import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.member.dto.response.MemberProfileResponse;
import kr.ac.kumoh.d138.JobForeigner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberProfileResponse getMemberProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new BusinessException(ExceptionType.MEMBER_NOT_FOUND));
        return MemberProfileResponse.toMemberProfileResponse(member);
    }

    @Transactional
    public void updateMemberProfile(Long memberId, MemberProfileRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new BusinessException(ExceptionType.MEMBER_NOT_FOUND));
        member.updateMemberProfile(request);
    }

    @Transactional
    public void updateProfileImage(ProfileImageRequest request, Long memberId) {

    }
}

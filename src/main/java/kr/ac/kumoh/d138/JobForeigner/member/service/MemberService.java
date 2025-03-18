package kr.ac.kumoh.d138.JobForeigner.member.service;

import kr.ac.kumoh.d138.JobForeigner.member.dto.response.MemberProfileResponse;
import kr.ac.kumoh.d138.JobForeigner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberProfileResponse getUserProfile(Long memberId) {
        return MemberProfileResponse.toMemberProfileResponse(memberRepository.findById(memberId).get());
    }

    public MemberProfileResponse getMyProfile(Long memberId) {
        return MemberProfileResponse.toMemberProfileResponse(memberRepository.findById(memberId).get());
    }

}

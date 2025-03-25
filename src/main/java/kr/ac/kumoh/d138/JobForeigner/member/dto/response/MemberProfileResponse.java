package kr.ac.kumoh.d138.JobForeigner.member.dto.response;

import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.member.domain.MemberType;

public record MemberProfileResponse (
        String name,
        MemberType type,
        String phoneNumber,
        String email,
        String profile_image_url
){
    public static MemberProfileResponse toMemberProfileResponse(Member member) {
        return new MemberProfileResponse(
                member.getName(),
                member.getType(),
                member.getPhoneNumber(),
                member.getEmail(),
                member.getProfile_image_url()
        );
    }
}

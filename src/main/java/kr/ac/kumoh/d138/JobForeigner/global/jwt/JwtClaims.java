package kr.ac.kumoh.d138.JobForeigner.global.jwt;

import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.member.domain.MemberType;

public record JwtClaims(
        Long memberId,
        MemberType memberType
) {
    public static JwtClaims create(Member member) {
        return new JwtClaims(member.getId(), member.getType());
    }
}

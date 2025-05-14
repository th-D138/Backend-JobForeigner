package kr.ac.kumoh.d138.JobForeigner.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberProfileRequest(
    // 이메일, 전화번호, 사는 곳
    String phoneNumber,
    @Email String email,
    String address,
    String detailAddress,
    String zipcode
) {
}

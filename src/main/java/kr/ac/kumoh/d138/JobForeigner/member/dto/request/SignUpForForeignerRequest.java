package kr.ac.kumoh.d138.JobForeigner.member.dto.request;

import java.time.LocalDate;

public record SignUpForForeignerRequest(
        String name,
        String username,
        String password,
        String type,
        String phoneNumber,
        String email,
        String gender,
        LocalDate birthDate,
        String profileImageUrl,
        String address,
        String detailAddress,
        String zipcode
) {
}

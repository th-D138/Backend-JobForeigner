package kr.ac.kumoh.d138.JobForeigner.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SignUpRequest(
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

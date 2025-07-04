package kr.ac.kumoh.d138.JobForeigner.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CompanySignUpRequest(
        @NotBlank(message = "담당자 이름은 필수 입력 항목입니다.")
        String name,
        @NotBlank(message = "기업 계정 아이디는 필수 입력 항목입니다.")
        String username,
        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Pattern(regexp = "^[\\x20-\\x7E]{8,}$", message = "8자 이상의 알파벳 대소문자, 숫자, 특수문자만 사용할 수 있습니다.")
        String password,
        @NotNull(message = "기업 아이디는 필수 입력 항목입니다.")
        Long companyId,
        @NotBlank(message = "담당자 전화번호는 필수 입력 항목입니다.")
        String phoneNumber,
        @NotBlank(message = "담당자 이메일은 필수 입력 항목입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,
        @NotBlank(message = "담당자 성별은 필수 입력 항목입니다.")
        @Pattern(regexp = "MALE|FEMALE", message = "성별은 MALE 또는 FEMALE이어야 합니다.")
        String gender,
        @NotNull(message = "담당자 생년월일은 필수 입력 항목입니다.")
        LocalDate birthDate,
        String profileImageUrl,
        @NotBlank(message = "기업 주소는 필수 입력 항목입니다.")
        String address,
        String detailAddress,
        @NotBlank(message = "기업 우편번호는 필수 입력 항목입니다.")
        String zipcode
) {
}

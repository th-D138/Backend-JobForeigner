package kr.ac.kumoh.d138.JobForeigner.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record SignUpForForeignerRequest(
        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        String name,
        @NotBlank(message = "아이디는 필수 입력 항목입니다.")
        String username,
        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Pattern(regexp = "^[\\x20-\\x7E]{8,}$", message = "8자 이상의 알파벳 대소문자, 숫자, 특수문자만 사용할 수 있습니다.")
        String password,
        @NotBlank(message = "사용자 유형은 필수 입력 항목입니다.")
        String type,
        @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
        String phoneNumber,
        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,
        @NotBlank(message = "성별은 필수 입력 항목입니다.")
        String gender,
        @NotNull(message = "생년월일은 필수 입력 항목입니다.")
        LocalDate birthDate,
        String profileImageUrl,
        @NotBlank(message = "주소는 필수 입력 항목입니다.")
        String address,
        String detailAddress,
        @NotBlank(message = "우편번호는 필수 입력 항목입니다.")
        String zipcode
) {
}

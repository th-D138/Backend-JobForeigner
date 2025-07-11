package kr.ac.kumoh.d138.JobForeigner.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CompanySignUpRequest(
        @Schema(description = "담당자 이름")
        @NotBlank(message = "담당자 이름은 필수 입력 항목입니다.")
        String name,

        @Schema(description = "비밀번호")
        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Pattern(regexp = "^[\\x20-\\x7E]{8,}$", message = "8자 이상의 알파벳 대소문자, 숫자, 특수문자만 사용할 수 있습니다.")
        String password,

        @NotNull(message = "기업 아이디는 필수 입력 항목입니다.")
        Long companyId,

        @Schema(description = "담당자 전화번호")
        @NotBlank(message = "담당자 전화번호는 필수 입력 항목입니다.")
        String phoneNumber,

        @Schema(description = "담당자 이메일 주소", example = "park@duck.com")
        @NotBlank(message = "담당자 이메일 주소는 필수 입력 항목입니다.")
        @Email(message = "올바른 이메일 주소 형식이 아닙니다.")
        String email,

        @Schema(description = "성별")
        @NotBlank(message = "담당자 성별은 필수 입력 항목입니다.")
        @Pattern(regexp = "MALE|FEMALE", message = "성별은 MALE 또는 FEMALE이어야 합니다.")
        String gender,

        @Schema(description = "담당자 생년월일", example = "1978-06-01")
        @NotNull(message = "담당자 생년월일은 필수 입력 항목입니다.")
        LocalDate birthDate,

        String profileImageUrl,

        @Schema(description = "기업 주소", example = "경상북도 구미시 대학로 61")
        @NotBlank(message = "기업 주소는 필수 입력 항목입니다.")
        String address,

        @Schema(description = "기업 상세 주소", example = "(양호동)")
        String detailAddress,

        @Schema(description = "기업 우편번호", example = "39177")
        @NotBlank(message = "기업 우편번호는 필수 입력 항목입니다.")
        String zipcode
) {
}

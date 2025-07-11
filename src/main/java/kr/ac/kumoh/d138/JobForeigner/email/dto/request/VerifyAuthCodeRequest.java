package kr.ac.kumoh.d138.JobForeigner.email.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VerifyAuthCodeRequest(
    @Schema(description = "이메일 주소", example = "park@duck.com")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    String email,

    @Schema(description = "인증 코드", example = "123456")
    @NotBlank(message = "인증 코드는 필수 입력 항목입니다.")
    @Pattern(regexp = "^\\d{6}$")
    String code
) {
}

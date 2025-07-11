package kr.ac.kumoh.d138.JobForeigner.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @Schema(description = "이메일 주소", example = "park@duck.com")
        @NotBlank(message = "이메일 주소는 필수 입력 항목입니다.")
        String email,

        @Schema(description = "비밀번호")
        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        String password
) {
}

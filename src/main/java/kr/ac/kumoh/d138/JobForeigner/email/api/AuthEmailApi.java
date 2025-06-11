package kr.ac.kumoh.d138.JobForeigner.email.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import kr.ac.kumoh.d138.JobForeigner.email.dto.request.ResendAuthMailRequest;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AuthEmailApi {
    @Operation(
            summary = "이메일 주소 인증 메일 재발송",
            description = "미인증된 회원이 이메일 주소 인증 메일 재발송 요청 시 인증 메일을 재발송합니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "이메일 주소 인증 메일 재발송 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.EMAIL_ALREADY_VERIFIED)
            }
    )
    ResponseEntity<ResponseBody<Void>> resendAuthMail(@RequestBody @Valid ResendAuthMailRequest resendAuthMailRequest);

    @Operation(
            summary = "이메일 주소 인증",
            description = "발급된 인증 코드를 포함하는 URL로 접속 시 이메일 주소 인증을 완료합니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "이메일 주소 인증 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.AUTH_CODE_INVALID),
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND)
            }
    )
    ResponseEntity<ResponseBody<Void>> verify(@RequestParam("code") @NotBlank String code);
}

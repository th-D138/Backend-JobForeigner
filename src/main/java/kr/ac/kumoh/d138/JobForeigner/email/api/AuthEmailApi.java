package kr.ac.kumoh.d138.JobForeigner.email.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.email.dto.request.SendAuthMailRequest;
import kr.ac.kumoh.d138.JobForeigner.email.dto.request.VerifyAuthCodeRequest;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "이메일 주소 인증 API", description = "이메일 주소 인증 관련 API")
public interface AuthEmailApi {
    @Operation(
            summary = "이메일 주소 인증 메일 발송",
            description = "이메일 주소 인증 메일 발송 요청 시 인증 메일을 발송합니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "이메일 주소 인증 메일 발송 성공"
            )
    )
    ResponseEntity<ResponseBody<Void>> sendAuthMail(@RequestBody @Valid SendAuthMailRequest sendAuthMailRequest);

    @Operation(
            summary = "이메일 주소 인증",
            description = "이메일 주소와 발급된 인증 코드를 요청으로 보내면 이메일 주소 인증을 완료합니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "이메일 주소 인증 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.AUTH_CODE_INVALID)
            }
    )
    ResponseEntity<ResponseBody<Void>> verify(@RequestBody @Valid VerifyAuthCodeRequest verifyAuthCodeRequest);
}

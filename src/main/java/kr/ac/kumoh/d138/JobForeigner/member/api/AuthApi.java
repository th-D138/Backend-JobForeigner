package kr.ac.kumoh.d138.JobForeigner.member.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.SignInRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthApi {
    @Operation(
            summary = "로그인",
            description = "로그인할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "로그인 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_INFO_INVALID),
                    @SwaggerApiFailedResponse(ExceptionType.EMAIL_VERIFICATION_REQUIRED)
            }
    )
    ResponseEntity<ResponseBody<Void>> signUp(@RequestBody SignInRequest signInRequest,
                                              HttpServletResponse response);

    @Operation(
            summary = "로그아웃",
            description = "로그아웃할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "로그아웃 성공"
            )
    )
    ResponseEntity<ResponseBody<Void>> signOut(@AuthenticationPrincipal Long memberId,
                                               HttpServletResponse response);
}

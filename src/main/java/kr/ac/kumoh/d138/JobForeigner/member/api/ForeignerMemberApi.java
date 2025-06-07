package kr.ac.kumoh.d138.JobForeigner.member.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.ForeignerSignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface ForeignerMemberApi {
    @Operation(
            summary = "외국인 사용자 회원가입",
            description = "회원가입을 할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    status = HttpStatus.OK,
                    description = "외국인 회원가입 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.USERNAME_ALREADY_EXISTS),
                    @SwaggerApiFailedResponse(ExceptionType.EMAIL_ALREADY_EXISTS)
            }
    )
    ResponseEntity<ResponseBody<Void>> signUp(@RequestBody @Valid ForeignerSignUpRequest signUpRequest);
}

package kr.ac.kumoh.d138.JobForeigner.member.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.BusinessNumberValidationRequest;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.CompanySignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface CompanyMemberApi {
    @Operation(
            summary = "사업자등록번호 검증",
            description = "사업자등록번호를 검증합니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "사업자등록번호 검증 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.BUSINESS_NUMBER_INVALID)
            }
    )
    ResponseEntity<ResponseBody<Void>> validateBusinessNumber(@RequestBody @Valid BusinessNumberValidationRequest businessNumberValidationRequest);

    @Operation(
            summary = "기업 사용자 회원가입",
            description = "회원가입을 할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    status = HttpStatus.OK,
                    description = "기업 회원 회원가입 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.COMPANY_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.USERNAME_ALREADY_EXISTS),
                    @SwaggerApiFailedResponse(ExceptionType.EMAIL_ALREADY_EXISTS),
                    @SwaggerApiFailedResponse(ExceptionType.COMPANY_ALREADY_EXISTS)
            }
    )
    ResponseEntity<ResponseBody<Void>> signUpForCompany(@RequestBody @Valid CompanySignUpRequest signUpRequest);
}

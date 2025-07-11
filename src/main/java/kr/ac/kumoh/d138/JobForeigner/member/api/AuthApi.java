package kr.ac.kumoh.d138.JobForeigner.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.annotation.CurrentMemberId;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.TokenUtils;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.ChangeEmailRequest;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.SignInRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthApi {
    @Tag(name = "인증 API", description = "인증 관련 API")
    @Operation(
            summary = "로그인",
            description = "사용자 로그인을 진행해 액세스 토큰과 리프레시 토큰을 획득할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "로그인이 성공적으로 완료되어 액세스 토큰 및 리프레시 토큰이 발급되었습니다."
            ),
            errors = {
                    @SwaggerApiFailedResponse(
                            value = ExceptionType.MEMBER_INFO_INVALID,
                            description = "요청으로 보낸 사용자 정보와 서버에 등록된 사용자 정보가 일치하지 않아 발생합니다."
                    )
            }
    )
    ResponseEntity<ResponseBody<Void>> signIn(@RequestBody SignInRequest signInRequest,
                                              HttpServletResponse response);

    @Tag(name = "인증 API", description = "인증 관련 API")
    @Operation(
            summary = "로그아웃",
            description = """
                    로그아웃을 진행해 리프레시 토큰을 삭제할 수 있습니다.<br>
                    액세스 토큰이 만료되지 않으면 로그인이 유효할 수 있습니다.
                    """
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "로그아웃이 성공적으로 완료되어 리프레시 토큰이 삭제되었습니다."
            )
    )
    ResponseEntity<ResponseBody<Void>> signOut(@CurrentMemberId Long memberId,
                                               @Parameter(description = "리프레시 토큰")
                                               @CookieValue(TokenUtils.COOKIE_NAME_REFRESH_TOKEN) Cookie refreshToken,
                                               HttpServletResponse response);

    @Tag(name = "계정 API", description = "계정 관련 API")
    @Operation(
            summary = "이메일 주소 변경",
            description = """
                    이메일 주소를 변경합니다. 이메일 인증 진행 후 이메일 주소를 변경해야 합니다.
                    """
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "이메일 주소 변경이 성공적으로 완료되었습니다."
            )
    )
    ResponseEntity<ResponseBody<Void>> changeEmail(@CurrentMemberId Long memberId,
                                                   @Valid ChangeEmailRequest changeEmailRequest);

    @Tag(name = "계정 API", description = "계정 관련 API")
    @Operation(
            summary = "회원탈퇴",
            description = """
                    회원탈퇴를 진행해 회원 정보와 리프레시 토큰을 삭제할 수 있습니다.<br>
                    액세스 토큰이 만료되지 않으면 인증 오류가 발생할 수 있습니다.
                    """
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "회원탈퇴가 성공적으로 완료되어 회원 정보 및 리프레시 토큰이 삭제되었습니다."
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND)
            }
    )
    ResponseEntity<ResponseBody<Void>> delete(@CurrentMemberId Long memberId,
                                              HttpServletResponse response);
}

package kr.ac.kumoh.d138.JobForeigner.token.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.TokenUtils;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "인증 API", description = "인증 관련 API")
public interface TokenApi {
    @Operation(
            summary = "액세스 토큰 및 리프레시 토큰 재발급",
            description = "액세스 토큰과 리프레시 토큰을 재발급하여 사용자가 로그인을 유지할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "액세스 토큰 및 리프레시 토큰의 발급이 성공적으로 완료되었습니다."
            ),
            errors = {
                    @SwaggerApiFailedResponse(
                            value = ExceptionType.JWT_INVALID,
                            description = """
                                    만료된 액세스 토큰이 API 서버에서 발급한 것이 아니거나 잘못된 문자열로 구성되었거나,<br>
                                    만료된 액세스 토큰의 사용자 정보가 리프레시 토큰을 소유한 사용자 정보와 일치하지 않을 때 발생합니다.
                                    """
                    ),
                    @SwaggerApiFailedResponse(
                            value = ExceptionType.JWT_NOT_EXIST,
                            description = "사용자가 로그아웃, 회원탈퇴 또는 리프레시 토큰의 만료 시 요청으로 들어온 리프레시 토큰이 서버에 존재하지 않을 때 발생합니다."
                    )
            }
    )
    ResponseEntity<ResponseBody<Void>> refresh(@Parameter(description = "액세스 토큰은 HTTP Authorization 헤더에 포함돼 서버로 전송되어야 합니다.", required = true)
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                               @Parameter(description = "리프레시 토큰은 Cookie에 포함돼 서버로 전송되어야 합니다.", required = true)
                                               @CookieValue(TokenUtils.COOKIE_NAME_REFRESH_TOKEN) Cookie refreshToken,
                                               HttpServletResponse response);
}

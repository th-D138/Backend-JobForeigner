package kr.ac.kumoh.d138.JobForeigner.token.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;

public interface TokenApi {
    String BEARER_PREFIX = "Bearer ";
    String COOKIE_NAME_REFRESH_TOKEN = "refresh_token";

    @Operation(
            summary = "액세스 토큰 재발급",
            description = "액세스 토큰과 리프레시 토큰을 재발급할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "액세스 토큰 및 리프레시 토큰 재발급 완료"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.JWT_INVALID),
                    @SwaggerApiFailedResponse(ExceptionType.JWT_NOT_EXIST)
            }
    )
    ResponseEntity<ResponseBody<Void>> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                               @CookieValue(COOKIE_NAME_REFRESH_TOKEN) Cookie refreshToken,
                                               HttpServletResponse response);
}

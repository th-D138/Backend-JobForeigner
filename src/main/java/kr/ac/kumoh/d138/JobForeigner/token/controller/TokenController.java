package kr.ac.kumoh.d138.JobForeigner.token.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.TokenUtils;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.token.api.TokenApi;
import kr.ac.kumoh.d138.JobForeigner.token.dto.JwtPair;
import kr.ac.kumoh.d138.JobForeigner.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class TokenController implements TokenApi {
    private final TokenService tokenService;

    @PostMapping("/refresh")
    public ResponseEntity<ResponseBody<Void>> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                                         @CookieValue(COOKIE_NAME_REFRESH_TOKEN) Cookie refreshToken,
                                                         HttpServletResponse response) {
        JwtPair tokens = JwtPair.of(authorization.substring(BEARER_PREFIX.length()), 0, refreshToken.getValue(), 0);
        JwtPair newTokens = tokenService.refresh(tokens);

        TokenUtils.setAccessTokenAndRefreshToken(response, newTokens);

        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }
}

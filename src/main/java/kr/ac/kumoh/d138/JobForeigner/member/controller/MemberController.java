package kr.ac.kumoh.d138.JobForeigner.member.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.SignInRequest;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.SignUpRequest;
import kr.ac.kumoh.d138.JobForeigner.member.service.MemberService;
import kr.ac.kumoh.d138.JobForeigner.token.dto.JwtPair;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String COOKIE_NAME_REFRESH_TOKEN = "refresh_token";

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseBody<Void>> signUp(@RequestBody @Valid SignUpRequest signUpRequest,
                                                     HttpServletResponse response) {
        JwtPair tokens = memberService.signUp(signUpRequest);

        response.addHeader(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokens.accessToken());

        Cookie cookie = new Cookie(COOKIE_NAME_REFRESH_TOKEN, tokens.refreshToken());
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        cookie.setPath("/tokens/refresh");
        cookie.setMaxAge(tokens.refreshTokenExpiredIn());
        response.addCookie(cookie);

        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseBody<Void>> signUp(@RequestBody SignInRequest signInRequest,
                                                     HttpServletResponse response) {
        JwtPair tokens = memberService.signIn(signInRequest.username(), signInRequest.password());

        response.addHeader(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokens.accessToken());

        Cookie cookie = new Cookie(COOKIE_NAME_REFRESH_TOKEN, tokens.refreshToken());
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        cookie.setPath("/tokens/refresh");
        cookie.setMaxAge(tokens.refreshTokenExpiredIn());
        response.addCookie(cookie);

        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    @DeleteMapping("/sign-out")
    public ResponseEntity<ResponseBody<Void>> signOut(@AuthenticationPrincipal Long memberId,
                                                      HttpServletResponse response) {
        memberService.signOut(memberId);

        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

}

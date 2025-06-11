package kr.ac.kumoh.d138.JobForeigner.member.controller;

import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.TokenUtils;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.member.api.AuthApi;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.SignInRequest;
import kr.ac.kumoh.d138.JobForeigner.member.service.AuthService;
import kr.ac.kumoh.d138.JobForeigner.token.dto.JwtPair;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;

    /**
     * 외국인 및 기업 사용자 로그인 API
     */
    @PostMapping("/sign-in")
    public ResponseEntity<ResponseBody<Void>> signIn(@RequestBody SignInRequest signInRequest,
                                                     HttpServletResponse response) {
        JwtPair tokens = authService.signIn(signInRequest.username(), signInRequest.password());
        TokenUtils.setAccessTokenAndRefreshToken(response, tokens);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    /**
     * 외국인 및 기업 사용자 로그아웃 API
     */
    @DeleteMapping("/sign-out")
    public ResponseEntity<ResponseBody<Void>> signOut(@AuthenticationPrincipal Long memberId,
                                                      HttpServletResponse response) {
        authService.signOut(memberId);
        TokenUtils.deleteRefreshToken(response);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }
}

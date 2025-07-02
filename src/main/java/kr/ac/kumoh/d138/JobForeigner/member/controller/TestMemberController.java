package kr.ac.kumoh.d138.JobForeigner.member.controller;

import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.TokenUtils;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.member.service.TestMemberService;
import kr.ac.kumoh.d138.JobForeigner.token.dto.JwtPair;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile({"local", "dev"})
@RestController
@RequestMapping("/api/test/members")
@RequiredArgsConstructor
public class TestMemberController {
    private final TestMemberService testMemberService;
    private final TokenUtils tokenUtils;

    @PostMapping("/sign-up/company")
    public ResponseEntity<ResponseBody<Void>> signUpForCompany(HttpServletResponse response) {
        JwtPair tokens = testMemberService.signUpForCompany();
        tokenUtils.setAccessTokenAndRefreshToken(response, tokens);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    @PostMapping("/sign-up/foreigner")
    public ResponseEntity<ResponseBody<Void>> signUpForForeigner(HttpServletResponse response) {
        JwtPair tokens = testMemberService.signUpForForeigner();
        tokenUtils.setAccessTokenAndRefreshToken(response, tokens);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }
}

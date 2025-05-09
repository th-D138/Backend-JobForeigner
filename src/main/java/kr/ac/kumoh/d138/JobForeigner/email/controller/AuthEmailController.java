package kr.ac.kumoh.d138.JobForeigner.email.controller;

import kr.ac.kumoh.d138.JobForeigner.email.service.AuthEmailService;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class AuthEmailController {
    private final AuthEmailService authEmailService;

    @PostMapping("/auth/resend")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> resendAuthMail(@AuthenticationPrincipal Long memberId) {
        authEmailService.sendMail(memberId);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    @PostMapping("/auth/verify")
    public ResponseEntity<ResponseBody<Void>> verify(@RequestParam("code") String code) {
        authEmailService.verifyEmail(code);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }
}

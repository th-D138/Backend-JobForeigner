package kr.ac.kumoh.d138.JobForeigner.email.controller;

import jakarta.validation.constraints.NotBlank;
import kr.ac.kumoh.d138.JobForeigner.email.dto.request.ResendAuthMailRequest;
import kr.ac.kumoh.d138.JobForeigner.email.service.AuthEmailService;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class AuthEmailController {
    private final AuthEmailService authEmailService;

    @PostMapping("/auth/resend")
    public ResponseEntity<ResponseBody<Void>> resendAuthMail(@RequestBody ResendAuthMailRequest resendAuthMailRequest) {
        authEmailService.sendMail(resendAuthMailRequest.email());
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    @PostMapping("/auth/verify")
    public ResponseEntity<ResponseBody<Void>> verify(@RequestParam("code") @NotBlank String code) {
        authEmailService.verifyEmail(code);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }
}

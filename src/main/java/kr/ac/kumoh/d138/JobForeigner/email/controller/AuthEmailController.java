package kr.ac.kumoh.d138.JobForeigner.email.controller;

import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.email.api.AuthEmailApi;
import kr.ac.kumoh.d138.JobForeigner.email.dto.request.SendAuthMailRequest;
import kr.ac.kumoh.d138.JobForeigner.email.dto.request.VerifyAuthCodeRequest;
import kr.ac.kumoh.d138.JobForeigner.email.service.AuthEmailService;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class AuthEmailController implements AuthEmailApi {
    private final AuthEmailService authEmailService;

    @PostMapping("/auth/send")
    public ResponseEntity<ResponseBody<Void>> sendAuthMail(@RequestBody @Valid SendAuthMailRequest sendAuthMailRequest) {
        authEmailService.sendMail(sendAuthMailRequest.email());
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    @PostMapping("/auth/verify")
    public ResponseEntity<ResponseBody<Void>> verify(@RequestBody @Valid VerifyAuthCodeRequest verifyAuthCodeRequest) {
        authEmailService.verifyEmail(verifyAuthCodeRequest);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }
}

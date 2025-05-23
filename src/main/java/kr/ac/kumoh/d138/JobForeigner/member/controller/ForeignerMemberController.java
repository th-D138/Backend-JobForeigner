package kr.ac.kumoh.d138.JobForeigner.member.controller;

import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.member.api.ForeignerMemberApi;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.ForeignerSignUpRequest;
import kr.ac.kumoh.d138.JobForeigner.member.service.ForeignerMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ForeignerMemberController implements ForeignerMemberApi {
    private final ForeignerMemberService foreignerMemberService;

    /**
     * 외국인 사용자 회원가입 API
     */
    @PostMapping("/sign-up/foreigner")
    public ResponseEntity<ResponseBody<Void>> signUp(@RequestBody @Valid ForeignerSignUpRequest signUpRequest) {
        foreignerMemberService.signUp(signUpRequest);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }
}

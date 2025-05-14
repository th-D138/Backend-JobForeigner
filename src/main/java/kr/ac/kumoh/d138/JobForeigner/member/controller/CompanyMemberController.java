package kr.ac.kumoh.d138.JobForeigner.member.controller;

import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.SignUpForCompanyRequest;
import kr.ac.kumoh.d138.JobForeigner.member.service.CompanyMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class CompanyMemberController {
    private final CompanyMemberService companyMemberService;

    /**
     * 기업 사용자 회원가입 API
     * 기업 사용자는 사업자 인증 후 승인이 완료되면 회원가입을 진행할 수 있습니다.
     */
    @PostMapping("/sign-up/company")
    public ResponseEntity<ResponseBody<Void>> signUpForComapny(@RequestBody @Valid SignUpForCompanyRequest signUpRequest) {
        companyMemberService.signUp(signUpRequest);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }
}

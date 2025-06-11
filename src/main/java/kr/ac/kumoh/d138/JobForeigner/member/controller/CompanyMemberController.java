package kr.ac.kumoh.d138.JobForeigner.member.controller;

import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.member.api.CompanyMemberApi;
import kr.ac.kumoh.d138.JobForeigner.member.client.BusinessNumberValidationClient;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.BusinessNumberValidationRequest;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.CompanySignUpRequest;
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
public class CompanyMemberController implements CompanyMemberApi {
    private final CompanyMemberService companyMemberService;

    private final BusinessNumberValidationClient businessNumberValidationClient;

    /**
     * 사업자등록번호 검증 API
     */
    @PostMapping("/sign-up/company/validate")
    public ResponseEntity<ResponseBody<Void>> validateBusinessNumber(@RequestBody @Valid BusinessNumberValidationRequest businessNumberValidationRequest) {
        // TODO: 사업자등록번호 검증 성공 시 인증 토큰을 발급하여 회원가입 API를 호출하게 할지, 회원가입 API에 사업자등록번호 검증을 함께 수행하게 할지 협의 필요
        businessNumberValidationClient.validateBusinessNumber(businessNumberValidationRequest);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }

    /**
     * 기업 사용자 회원가입 API
     * 기업 사용자는 사업자 인증 후 승인이 완료되면 회원가입을 진행할 수 있습니다.
     */
    @PostMapping("/sign-up/company")
    public ResponseEntity<ResponseBody<Void>> signUpForCompany(@RequestBody @Valid CompanySignUpRequest signUpRequest) {
        companyMemberService.signUp(signUpRequest);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }
}

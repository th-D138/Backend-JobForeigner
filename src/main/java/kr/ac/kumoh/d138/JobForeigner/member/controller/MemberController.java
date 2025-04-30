package kr.ac.kumoh.d138.JobForeigner.member.controller;

import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.member.dto.response.MemberProfileResponse;
import kr.ac.kumoh.d138.JobForeigner.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil.createSuccessResponse;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 사용자의 프로필 정보 반환
    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseBody<MemberProfileResponse>> getUserProfile(@PathVariable Long memberId) {
        return ResponseEntity.ok(createSuccessResponse(memberService.getMemberProfile(memberId)));
    }

    // 자신의 프로필 정보 반환
    // TODO: 멤버의 아이디를 토큰에서 받아와야함
    @GetMapping("/me")
    public ResponseEntity<ResponseBody<MemberProfileResponse>> getMyProfile(Long memberId) {
        return ResponseEntity.ok(createSuccessResponse(memberService.getMemberProfile(memberId)));
    }
}

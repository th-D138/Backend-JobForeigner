package kr.ac.kumoh.d138.JobForeigner.member.controller;

import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.authentication.JwtAuthentication;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.member.api.MemberApi;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.MemberProfileRequest;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.ProfileImageRequest;
import kr.ac.kumoh.d138.JobForeigner.member.dto.response.MemberProfileResponse;
import kr.ac.kumoh.d138.JobForeigner.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil.createSuccessResponse;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController implements MemberApi {
    private final MemberService memberService;

    // TODO: 커뮤니티 기능 개발되면 추가
//    // 사용자의 프로필 정보 반환
//    @GetMapping("/{memberId}")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<ResponseBody<MemberProfileResponse>> getUserProfile(@PathVariable Long memberId) {
//        return ResponseEntity.ok(createSuccessResponse(memberService.getMemberProfile(memberId)));
//    }

    // 자신의 프로필 정보 반환
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<MemberProfileResponse>> getMyProfile(
            @AuthenticationPrincipal Long memberId) {
        return ResponseEntity.ok(createSuccessResponse(memberService.getMemberProfile(memberId)));
    }

    // 자신의 프로필 정보 변경
    @PatchMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> updateMyProfile(
            @RequestBody @Valid MemberProfileRequest request,
            @AuthenticationPrincipal Long memberId){
        memberService.updateMemberProfile(memberId, request);
        return ResponseEntity.ok(createSuccessResponse());
    }

    // 프로필 사진 변경
    // TODO: 서버컴 세팅 완료하기
    @PatchMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> updateProfileImage(
            @ModelAttribute ProfileImageRequest request,
            @AuthenticationPrincipal Long memberId){
        memberService.updateProfileImage(request, memberId);
        return ResponseEntity.ok(createSuccessResponse());
    }
}

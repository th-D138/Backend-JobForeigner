package kr.ac.kumoh.d138.JobForeigner.resume.controller;

import kr.ac.kumoh.d138.JobForeigner.global.jwt.annotation.CurrentMemberId;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.resume.api.ResumeApi;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;
import kr.ac.kumoh.d138.JobForeigner.resume.dto.request.ResumeRequest;
import kr.ac.kumoh.d138.JobForeigner.resume.dto.response.ResumeResponse;
import kr.ac.kumoh.d138.JobForeigner.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil.*;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController implements ResumeApi {
    private final ResumeService resumeService;

    // 이력서 작성
    // 이미지 저장 방식: 사용자에게 이미지를 받고 서버에 저장 후 저장된 주소를 db에 저장
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> createResume(
            @RequestPart ResumeRequest resumeRequest,
            @RequestPart(required = false) MultipartFile image,
            @CurrentMemberId Long memberId) {
        resumeService.createResume(resumeRequest, image, memberId);
        return ResponseEntity.ok(createSuccessResponse());
    }

    // 사용자는 자신의 이력서 한 개를 불러옴
    // memberId로 member를 식별하고
    // member의 resumeId로 해당 이력서를 불러옴
    @GetMapping("/{resumeId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<ResumeResponse>> getResume(
            @CurrentMemberId Long memberId,
            @PathVariable Long resumeId) {
        Resume resume = resumeService.getResume(memberId, resumeId);
        return ResponseEntity.ok(createSuccessResponse(ResumeResponse.toResumeResponse(resume)));
    }

    // 사용자의 전체 이력서 반환
    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Page<ResumeResponse>>> getAllResumes
            (@CurrentMemberId Long memberId,
             @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResumeResponse> resumeResponses = resumeService.getAllResume(memberId, pageable);
        return ResponseEntity.ok(createSuccessResponse(resumeResponses));
    }

    @PatchMapping(value = "/{resumeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<ResumeResponse>> updateResume(
        @RequestPart ResumeRequest resumeRequest,
        @RequestPart MultipartFile image,
        @CurrentMemberId Long memberId,
        @PathVariable Long resumeId
    ) {
        ResumeResponse response = resumeService.updateResume(resumeRequest, image, memberId, resumeId);
        return ResponseEntity.ok(createSuccessResponse(response));
    }

    // 사용자의 이력서 삭제
    @DeleteMapping("/{resumeId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> deleteResume(@PathVariable Long resumeId, @CurrentMemberId Long memberId){
        resumeService.deleteResume(resumeId, memberId);
        return ResponseEntity.ok(createSuccessResponse());
    }

}

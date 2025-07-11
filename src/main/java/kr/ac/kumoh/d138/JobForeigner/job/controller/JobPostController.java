package kr.ac.kumoh.d138.JobForeigner.job.controller;

import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.annotation.CurrentMemberId;
import kr.ac.kumoh.d138.JobForeigner.global.response.GlobalPageResponse;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.job.api.JobPostApi;
import kr.ac.kumoh.d138.JobForeigner.job.dto.request.JobPostRequestDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.request.JobTempPostRequestDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.JobPostDetailResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.JobPostResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.UpdateJobPostResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.service.JobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/job-posts")
public class JobPostController implements JobPostApi {

    private final JobPostService jobPostService;

    /*
    채용공고 임시저장
     */
    @PostMapping("/temp")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<String>>  saveTemporaryJobPost(@Valid @RequestBody JobTempPostRequestDto jobTempPostRequestDto){
        jobPostService.saveTemporaryJobPost(jobTempPostRequestDto);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse("임시 저장 완료"));
    }

    /*
    채용공고 작성
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<String>>  createJobPost(@Valid @RequestBody JobPostRequestDto jobPostRequestDto){
        jobPostService.createJobPost(jobPostRequestDto);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse("채용공고 작성 완료"));
    }

    /*
    채용공고 조회
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<GlobalPageResponse<JobPostResponseDto>>> showJobPost(
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String jobType,
            @PageableDefault(size=12, sort= "companyName") Pageable pageable
            ){
            Page<JobPostResponseDto> allJobPost = jobPostService.getAllJobPost(companyName, region, jobType, pageable);
            return ResponseEntity.ok(ResponseUtil.createSuccessResponse(GlobalPageResponse.create(allJobPost)));
    }

    /*
    채용공고 상세 페이지
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<JobPostDetailResponseDto>> showJobPostDetail(@PathVariable Long id, @CurrentMemberId Long memberId){
        JobPostDetailResponseDto jobPostDetail = jobPostService.getJobPostDetail(id, memberId);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(jobPostDetail));
    }

    /*
    채용공고 수정
     */
    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<UpdateJobPostResponseDto>> updateJobPost(@PathVariable Long id, @Valid @RequestBody JobPostRequestDto jobPostRequestDto){
        UpdateJobPostResponseDto jobPostResponseDto = jobPostService.updateJobPost(id, jobPostRequestDto);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(jobPostResponseDto));
    }

    /*
    채용공고 삭제
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<String>> deleteJobPost(@PathVariable Long id){
        jobPostService.deleteJobPost(id);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse("채용공고 삭제 완료"));
    }

    @PostMapping("/jobPost/{resumeId}/{jobPostId}/apply")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<String>> applyToJobPost(
            @PathVariable Long jobPostId,
            @CurrentMemberId Long memberId,
            @PathVariable Long resumeId) {
        jobPostService.applyToJobPost(jobPostId, memberId, resumeId);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse("채용공고 지원 완료"));
    }
}

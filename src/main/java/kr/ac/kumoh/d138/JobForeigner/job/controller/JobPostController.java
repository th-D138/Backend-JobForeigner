package kr.ac.kumoh.d138.JobForeigner.job.controller;

import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.global.response.GlobalPageResponse;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.job.dto.company.request.JobPostRequestDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.company.request.JobTempPostRequestDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.company.response.JobPostResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.service.JobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
public class JobPostController {

    private final JobPostService jobPostService;
    /*
    채용공고 임시저장
     */
    @PostMapping("/jobPost/temp")
    public ResponseEntity<ResponseBody<String>>  saveTemporaryJobPost(@Valid @RequestBody JobTempPostRequestDto jobTempPostRequestDto){
        jobPostService.saveTemporaryJobPost(jobTempPostRequestDto);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse("임시 저장 완료"));
    }

    /*
    채용공고 작성
     */
    @PostMapping("/jobPost")
    public ResponseEntity<ResponseBody<String>>  createJobPost(@Valid @RequestBody JobPostRequestDto jobPostRequestDto){
        jobPostService.createJobPost(jobPostRequestDto);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse("채용공고 작성 완료"));
    }

    /*
    채용공고 조회
     */
    @GetMapping("/jobPost")
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
    @GetMapping("/jobPost/{id}")
    public ResponseEntity<ResponseBody<JobPostResponseDto>> showJobPostDetail(@PathVariable Long id){
        JobPostResponseDto jobPostDetail = jobPostService.getJobPostDetail(id);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(jobPostDetail));
    }

    /*
    채용공고 수정
     */
    @PatchMapping("/jobPost/{id}")
    public ResponseEntity<ResponseBody<JobPostResponseDto>> updateJobPost(@PathVariable Long id,@RequestBody JobPostRequestDto jobPostRequestDto){
        JobPostResponseDto jobPostResponseDto = jobPostService.updateJobPost(id, jobPostRequestDto);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(jobPostResponseDto));
    }

    /*
    채용공고 삭제
     */
    @DeleteMapping("/jobPost/{id}")
    public ResponseEntity<ResponseBody<String>> deleteJobPost(@PathVariable Long id){
        jobPostService.deleteJobPost(id);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse("채용공고 삭제 완료"));
    }
    /*
    채용공고 스크랩
     */

}

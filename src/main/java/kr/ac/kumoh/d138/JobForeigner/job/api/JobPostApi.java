package kr.ac.kumoh.d138.JobForeigner.job.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.annotation.CurrentMemberId;
import kr.ac.kumoh.d138.JobForeigner.global.response.GlobalPageResponse;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.job.dto.request.JobPostRequestDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.request.JobTempPostRequestDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.JobPostDetailResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.JobPostResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.UpdateJobPostResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "채용공고 API", description = "채용공고 관련 API")
public interface JobPostApi {

    @Operation(
            summary = "채용공고 임시저장",
            description = "채용공고를 임시저장할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "임시저장 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.COMPANY_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.BINDING_ERROR)
            }
    )
    ResponseEntity<ResponseBody<String>> saveTemporaryJobPost(
            @Valid @RequestBody JobTempPostRequestDto jobTempPostRequestDto
    );

    @Operation(
            summary = "채용공고 작성",
            description = "채용공고를 작성할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "채용공고 작성 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.COMPANY_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.BINDING_ERROR)
            }
    )
    ResponseEntity<ResponseBody<String>> createJobPost(
            @Valid @RequestBody JobPostRequestDto jobPostRequestDto
    );

    @Operation(
            summary = "채용공고 조회",
            description = "채용공고 목록을 조회할 수 있습니다. 기업명, 지역, 직종으로 필터링 가능합니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "채용공고 조회 성공",
                    responsePage = JobPostResponseDto.class
            )
    )
    ResponseEntity<ResponseBody<GlobalPageResponse<JobPostResponseDto>>> showJobPost(
            @Parameter(description = "기업명 검색 키워드")
            @RequestParam(required = false) String companyName,
            @Parameter(description = "지역 검색 키워드")
            @RequestParam(required = false) String region,
            @Parameter(description = "직종 검색 키워드")
            @RequestParam(required = false) String jobType,
            @Parameter(description = "페이징 정보 (기본 크기: 12, 정렬: companyName)")
            @PageableDefault(size = 12, sort = "companyName") Pageable pageable
    );

    @Operation(
            summary = "채용공고 상세 조회",
            description = "특정 채용공고의 상세 정보를 조회할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "채용공고 상세 조회 성공",
                    response = JobPostDetailResponseDto.class
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.JOBPOST_NOT_FOUND)
            }
    )
    ResponseEntity<ResponseBody<JobPostDetailResponseDto>> showJobPostDetail(
            @Parameter(description = "채용공고 ID", required = true)
            @PathVariable Long id,
            @CurrentMemberId Long memberId
    );

    @Operation(
            summary = "채용공고 수정",
            description = "채용공고를 수정할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "채용공고 수정 성공",
                    response = UpdateJobPostResponseDto.class
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.JOBPOST_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.BINDING_ERROR)
            }
    )
    ResponseEntity<ResponseBody<UpdateJobPostResponseDto>> updateJobPost(
            @Parameter(description = "채용공고 ID", required = true)
            @PathVariable Long id,
            @Valid @RequestBody JobPostRequestDto jobPostRequestDto
    );

    @Operation(
            summary = "채용공고 삭제",
            description = "채용공고를 삭제할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "채용공고 삭제 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.JOBPOST_NOT_FOUND)
            }
    )
    ResponseEntity<ResponseBody<String>> deleteJobPost(
            @Parameter(description = "채용공고 ID", required = true)
            @PathVariable Long id
    );

    @Operation(
            summary = "채용공고 지원",
            description = "특정 채용공고에 지원할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "채용공고 지원 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.JOBPOST_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.RESUME_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.DUPLICATED_APPLICATION),
                    @SwaggerApiFailedResponse(ExceptionType.EXPIRED_JOB_POST)
            }
    )
    ResponseEntity<ResponseBody<String>> applyToJobPost(
            @Parameter(description = "채용공고 ID", required = true)
            @PathVariable Long jobPostId,
            @CurrentMemberId Long memberId,
            @Parameter(description = "이력서 ID", required = true)
            @PathVariable Long resumeId
    );
}

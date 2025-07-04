package kr.ac.kumoh.d138.JobForeigner.resume.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.resume.dto.request.ResumeRequest;
import kr.ac.kumoh.d138.JobForeigner.resume.dto.response.ResumeResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeApi {

    @Operation(
            summary = "이력서 생성",
            description = "사용자는 새로운 이력서를 생성합니다",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "이력서 생성 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.FILE_SYSTEM_SAVE_FAILED)
            }
    )

    ResponseEntity<ResponseBody<Void>> createResume(
            @Parameter(
                    description = "이력서 정보",
                    schema = @Schema(implementation = ResumeRequest.class)
            )
            @RequestPart ResumeRequest resumeRequest,

            @Parameter(
                    description = "이력서 프로필 이미지",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestPart(required = false) MultipartFile image,

            @Parameter(hidden = true)
            @AuthenticationPrincipal Long memberId
    );


    @Operation(
            summary = "이력서 상세 조회",
            description = "사용자 자신의 특정 이력서를 조회합니다",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = ResumeResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    response = ResumeResponse.class,
                    description = "이력서 조회 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.RESUME_FORBIDDEN)
            }
    )
    ResponseEntity<ResponseBody<ResumeResponse>> getResume(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long memberId,

            @Parameter(description = "조회할 이력서 ID", example = "1")
            @PathVariable Long resumeId
    );

    // 이력서 목록 조회
    @Operation(
            summary = "이력서 목록 조회",
            description = "사용자 자신의 모든 이력서를 페이지네이션하여 조회합니다",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResumeResponse.class))))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    responsePage = ResumeResponse.class,
                    description = "이력서 목록 조회 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED)
            }
    )
    ResponseEntity<ResponseBody<Page<ResumeResponse>>> getAllResumes(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long memberId,

            @ParameterObject
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC)
            Pageable pageable
    );

    // 이력서 수정
    @Operation(
            summary = "이력서 수정",
            description = "사용자 자신의 특정 이력서를 수정합니다",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = ResumeResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    response = ResumeResponse.class,
                    description = "이력서 수정 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.RESUME_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.RESUME_FORBIDDEN),
                    @SwaggerApiFailedResponse(ExceptionType.FILE_SYSTEM_SAVE_FAILED)
            }
    )
    ResponseEntity<ResponseBody<ResumeResponse>> updateResume(
            @Parameter(
                    description = "수정할 이력서 정보",
                    schema = @Schema(implementation = ResumeRequest.class)
            )
            @RequestPart ResumeRequest resumeRequest,

            @Parameter(
                    description = "이력서 프로필 이미지",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestPart MultipartFile image,

            @Parameter(hidden = true)
            @AuthenticationPrincipal Long memberId,

            @Parameter(description = "수정할 이력서 ID", example = "1")
            @PathVariable Long resumeId
    );

    // 이력서 삭제
    @Operation(
            summary = "이력서 삭제",
            description = "사용자 자신의 특정 이력서를 삭제합니다",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "이력서 삭제 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.RESUME_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.RESUME_FORBIDDEN)
            }
    )
    ResponseEntity<ResponseBody<Void>> deleteResume(
            @Parameter(description = "삭제할 이력서 ID", example = "1")
            @PathVariable Long resumeId,

            @Parameter(hidden = true)
            @AuthenticationPrincipal Long memberId
    );

}

package kr.ac.kumoh.d138.JobForeigner.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.annotation.CurrentMemberId;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.MemberProfileRequest;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.ProfileImageRequest;
import kr.ac.kumoh.d138.JobForeigner.member.dto.response.MemberProfileResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberApi {
    @Operation(
            summary = "사용자 프로필 조회",
            description = "사용자 자신의 프로필 정보를 조회합니다",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponse(content = @Content(schema = @Schema(implementation = MemberProfileResponse.class)))
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    response = MemberProfileResponse.class,
                    description = "프로필 조회 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND)
            }
    )
    ResponseEntity<ResponseBody<MemberProfileResponse>> getMyProfile(
            @CurrentMemberId Long memberId);


    @Operation(
            summary = "사용자 프로필 변경",
            description = "사용자 자신의 프로필 정보를 변경합니다",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "프로필 변경 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND)
            }
    )
    ResponseEntity<ResponseBody<Void>> updateMyProfile(
            @RequestBody @Valid MemberProfileRequest request,
            @CurrentMemberId Long memberId);


    @Operation(
            summary = "사용자 프로필 이미지 변경",
            description = "사용자 자신의 프로필 이미지를 변경합니다",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "프로필 이미지 변경 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.NEED_AUTHORIZED),
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.FILE_SYSTEM_SAVE_FAILED)
            }
    )
    ResponseEntity<ResponseBody<Void>> updateProfileImage(
            @Parameter(
                    description = "프로필 이미지 파일",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @ModelAttribute ProfileImageRequest request,
            @CurrentMemberId Long memberId);
}

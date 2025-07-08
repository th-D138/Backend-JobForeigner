package kr.ac.kumoh.d138.JobForeigner.scrap.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.scrap.dto.ScrapResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "스크랩 API", description = "채용공고 스크랩 관련 API")
public interface ScrapApi {

    @Operation(
            summary = "채용공고 스크랩 토글",
            description = "채용공고를 스크랩하거나 스크랩을 해제할 수 있습니다. " +
                    "이미 스크랩된 채용공고라면 스크랩이 해제되고, " +
                    "스크랩되지 않은 채용공고라면 스크랩이 추가됩니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "스크랩 토글 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.JOBPOST_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.ACCESS_DENIED)
            }
    )
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<ResponseBody<ScrapResponse>> toggleScrap(
            @AuthenticationPrincipal Long memberId,
            @Parameter(description = "채용공고 ID", required = true)
            @PathVariable Long jobPostId
    );
}
package kr.ac.kumoh.d138.JobForeigner.job.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.response.GlobalPageResponse;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.CompanyDetailResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.CompanyResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "기업 API", description = "기업 정보 관련 API")
public interface CompanyApi {

    @Operation(
            summary = "기업 목록 조회",
            description = "기업 목록을 조회할 수 있습니다. 기업명, 지역, 직종으로 필터링 가능합니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "기업 목록 조회 성공"
            )
    )
    ResponseEntity<ResponseBody<GlobalPageResponse<CompanyResponseDto>>> getAllCompany(
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
            summary = "기업 상세 조회",
            description = "특정 기업의 상세 정보를 조회할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "기업 상세 조회 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.COMPANY_NOT_FOUND)
            }
    )
    ResponseEntity<ResponseBody<CompanyDetailResponseDto>> getCompanyDetail(
            @Parameter(description = "기업 ID", required = true)
            @PathVariable("companyId") Long companyId
    );
}
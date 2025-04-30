package kr.ac.kumoh.d138.JobForeigner.job.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.response.GlobalPageResponse;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.job.dto.CompanyDetailResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.CompanyResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface CompanyApi {

    /*
    기업 리스트 전체조회
     */
    @Operation(
            summary = "기업 정보 전체 조회",
            description = "기업 정보를 필터링 조건에 따라 페이지네이션하여 전체 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "기업 전체 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CompanyResponseDto.class))
                    )
            )
    })
    @GetMapping("/company")
    public ResponseEntity<ResponseBody<GlobalPageResponse<CompanyResponseDto>>> getAllCompany(
            @Parameter(description = "기업 이름 검색", example = "google")
            @RequestParam(required = false) String companyName,

            @Parameter(description = "지역 필터링", example = "서울")
            @RequestParam(required = false) String region,

            @Parameter(description = "직종 필터링", example = "쇼핑몰")
            @RequestParam(required = false) String jobType,

            @ParameterObject // Pageable은 springdoc에서 이렇게 처리
            @PageableDefault(size = 12, sort = "companyName") Pageable pageable
    );

    /*
    기업 상세조회
     */
    @Operation(
            summary = "기업 정보 상세조회",
            description = "기업 ID를 기반으로 상세한 기업 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "기업 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = CompanyDetailResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "회사를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ResponseBody.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = ResponseBody.class))
            )
    })
    @GetMapping("/company/{companyId}")
    public ResponseEntity<ResponseBody<CompanyDetailResponseDto>> getCompanyDetail(
            @Parameter(description = "회사 아이디", example = "1", required = true)
            @PathVariable("companyId") Long companyId
    );
}

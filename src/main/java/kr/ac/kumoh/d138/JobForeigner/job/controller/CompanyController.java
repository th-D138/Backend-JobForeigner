package kr.ac.kumoh.d138.JobForeigner.job.controller;

import kr.ac.kumoh.d138.JobForeigner.global.response.GlobalPageResponse;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.job.api.CompanyApi;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.CompanyDetailResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.CompanyResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController implements CompanyApi {

    private final CompanyService companyService;
    /*
    기업 리스트 전체조회
     */
    @GetMapping
    public ResponseEntity<ResponseBody<GlobalPageResponse<CompanyResponseDto>>> getAllCompany(
            @RequestParam(required = false) String companyName,

            @RequestParam(required = false) String region,

            @RequestParam(required = false) String jobType,

            @PageableDefault(size = 12, sort = "companyName") Pageable pageable
    ) {
        Page<CompanyResponseDto> allCompany = companyService.getAllCompany(companyName, region, jobType, pageable);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(GlobalPageResponse.create(allCompany)));
    }

    /*
    기업 상세조회
     */
    @GetMapping("/{companyId}")
    public ResponseEntity<ResponseBody<CompanyDetailResponseDto>> getCompanyDetail(
            @PathVariable("companyId") Long companyId
    ) {
        CompanyDetailResponseDto companyDetail = companyService.getCompanyDetail(companyId);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(companyDetail));
    }

}



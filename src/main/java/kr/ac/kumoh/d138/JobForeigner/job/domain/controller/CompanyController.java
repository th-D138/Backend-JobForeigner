package kr.ac.kumoh.d138.JobForeigner.job.domain.controller;

import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.job.domain.dto.CompanyDetailResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.domain.dto.CompanyResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.domain.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    /*
    기업 리스트 전체조회
     */
    @GetMapping("/company")
    public ResponseEntity<ResponseBody<Page<CompanyResponseDto>>> getAllCompany(@PageableDefault(size=12, sort="companyName")  Pageable pageable){
        Page<CompanyResponseDto> allCompany = companyService.getAllCompany(pageable);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(allCompany));
    }

    /*
    기업 상세조회
     */
    @GetMapping("/company/{companyId}")
    public ResponseEntity<ResponseBody<CompanyDetailResponseDto>> getCompanyDetail(@PathVariable("companyId") Long companyId){
        CompanyDetailResponseDto companyDetail = companyService.getCompanyDetail(companyId);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(companyDetail));
    }

}

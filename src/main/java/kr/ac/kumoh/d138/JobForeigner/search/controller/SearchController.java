package kr.ac.kumoh.d138.JobForeigner.search.controller;

import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.search.dto.response.MainSearchResponse;
import kr.ac.kumoh.d138.JobForeigner.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public void mainSearch(
            @RequestParam(required = true) String keyword,
            @PageableDefault(size=12, sort= "companyName") Pageable pageable
    ) {
        Page<MainSearchResponse> response = searchService.searchAll(keyword, pageable);
        ResponseEntity.ok(ResponseUtil.createSuccessResponse(response));
    }
}

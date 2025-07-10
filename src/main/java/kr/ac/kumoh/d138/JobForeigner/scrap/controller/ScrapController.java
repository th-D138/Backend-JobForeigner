package kr.ac.kumoh.d138.JobForeigner.scrap.controller;

import kr.ac.kumoh.d138.JobForeigner.global.jwt.annotation.CurrentMemberId;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.scrap.dto.ScrapResponse;
import kr.ac.kumoh.d138.JobForeigner.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/scrap")
public class ScrapController {

    private final ScrapService scrapService;

    @PostMapping("/{jobPostId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<ScrapResponse>> toggleScrap(
            @CurrentMemberId Long memberId,
            @PathVariable Long jobPostId
    ) {
        ScrapResponse dto = scrapService.toggleScrap(memberId, jobPostId);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(dto));
    }
}

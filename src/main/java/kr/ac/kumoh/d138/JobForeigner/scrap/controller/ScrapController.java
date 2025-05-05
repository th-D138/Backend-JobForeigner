package kr.ac.kumoh.d138.JobForeigner.scrap.controller;

import kr.ac.kumoh.d138.JobForeigner.global.jwt.authentication.JwtAuthentication;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.scrap.dto.ScrapResponse;
import kr.ac.kumoh.d138.JobForeigner.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scrap")
public class ScrapController {

    private final ScrapService scrapService;

    @PostMapping("/{jobPostId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<ScrapResponse>> toggleScrap(
            @AuthenticationPrincipal JwtAuthentication auth,
            @PathVariable Long jobPostId
    ) {
        ScrapResponse dto = scrapService.toggleScrap(auth.memberId(), jobPostId);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(dto));
    }
}

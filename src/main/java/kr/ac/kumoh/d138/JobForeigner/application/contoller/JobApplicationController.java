package kr.ac.kumoh.d138.JobForeigner.application.contoller;

import kr.ac.kumoh.d138.JobForeigner.application.dto.ResumeResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobApplicationController {

    // 이력서 선택 페이지 GET
    @GetMapping("/application")
    @PreAuthorize("hasRole(isAuthenticated())")
    public ResponseEntity<List<ResumeResponseDto>> getResumeList(@AuthenticationPrincipal Long id) {

    }
    // 이력서 선택
}

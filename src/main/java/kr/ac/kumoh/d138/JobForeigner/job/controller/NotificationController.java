package kr.ac.kumoh.d138.JobForeigner.job.controller;

import kr.ac.kumoh.d138.JobForeigner.global.jwt.annotation.CurrentMemberId;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseUtil;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.NotificationResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService alarmService;

    /*
    읽지 않은 알림 개수 조회
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Integer>> getUnreadCount(@CurrentMemberId Long memberId){
        int count = alarmService.getUnreadCount(memberId);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(count));
    }

    /*
    모든 알림 전체 조회
     */
    @GetMapping("/recent")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<List<NotificationResponseDto>>> getRecentNotification(@CurrentMemberId Long memberId){
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(alarmService.getRecentNotification(memberId)));
    }

    /*
    읽은 알림 표시
     */
    @PostMapping("/{notificationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<NotificationResponseDto>> checkReadNotification(@PathVariable Long notificationId, @CurrentMemberId Long memberId){
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse(alarmService.checkReadNotification(memberId, notificationId)));
    }

    /*
    알림 삭제하기
     */
    @DeleteMapping("/{notificationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<Void>> deleteNotification(@PathVariable Long notificationId, @CurrentMemberId Long memberId){
        alarmService.deleteNotification(notificationId, memberId);
        return ResponseEntity.ok(ResponseUtil.createSuccessResponse());
    }
}

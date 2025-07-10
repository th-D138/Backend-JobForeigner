package kr.ac.kumoh.d138.JobForeigner.job.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiFailedResponse;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiResponses;
import kr.ac.kumoh.d138.JobForeigner.global.config.swagger.SwaggerApiSuccessResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.annotation.CurrentMemberId;
import kr.ac.kumoh.d138.JobForeigner.global.response.ResponseBody;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.NotificationResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "알림 API", description = "알림 관련 API")
public interface NotificationApi {

    @Operation(
            summary = "읽지 않은 알림 개수 조회",
            description = "로그인한 사용자의 읽지 않은 알림 개수를 조회할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "읽지 않은 알림 개수 조회 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND)
            }
    )
    ResponseEntity<ResponseBody<Integer>> getUnreadCount(
            @CurrentMemberId Long memberId
    );

    @Operation(
            summary = "모든 알림 조회",
            description = "로그인한 사용자의 모든 알림을 조회할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "알림 조회 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND)
            }
    )
    ResponseEntity<ResponseBody<List<NotificationResponseDto>>> getRecentNotification(
            @CurrentMemberId Long memberId
    );

    @Operation(
            summary = "알림 읽음 처리",
            description = "특정 알림을 읽음 상태로 변경할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "알림 읽음 처리 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.NOTIFICATION_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.ACCESS_DENIED)
            }
    )
    ResponseEntity<ResponseBody<NotificationResponseDto>> checkReadNotification(
            @Parameter(description = "알림 ID") @PathVariable Long notificationId,
            @CurrentMemberId Long memberId
    );

    @Operation(
            summary = "알림 삭제",
            description = "특정 알림을 삭제할 수 있습니다."
    )
    @SwaggerApiResponses(
            success = @SwaggerApiSuccessResponse(
                    description = "알림 삭제 성공"
            ),
            errors = {
                    @SwaggerApiFailedResponse(ExceptionType.MEMBER_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.NOTIFICATION_NOT_FOUND),
                    @SwaggerApiFailedResponse(ExceptionType.ACCESS_DENIED)
            }
    )
    ResponseEntity<ResponseBody<Void>> deleteNotification(
            @Parameter(description = "알림 ID") @PathVariable Long notificationId,
            @CurrentMemberId Long memberId
    );
}

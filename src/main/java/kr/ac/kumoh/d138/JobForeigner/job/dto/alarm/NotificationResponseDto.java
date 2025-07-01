package kr.ac.kumoh.d138.JobForeigner.job.dto.alarm;

import kr.ac.kumoh.d138.JobForeigner.job.domain.Notification;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationResponseDto {

    private Long id;
    private String title;
    private String content;
    private boolean isRead;


    @Builder
    public NotificationResponseDto(Long id, String title, String content, boolean isRead) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isRead = isRead;
    }

    public static NotificationResponseDto fromEntity(Notification notification) {
        return NotificationResponseDto.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .isRead(notification.isRead())
                .build();
    }
}

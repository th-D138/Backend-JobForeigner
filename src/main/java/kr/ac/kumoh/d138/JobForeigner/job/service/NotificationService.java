
package kr.ac.kumoh.d138.JobForeigner.job.service;

import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.job.domain.Notification;
import kr.ac.kumoh.d138.JobForeigner.job.dto.alarm.NotificationResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private static final int NOTIFICATION_DAYS_BEFORE = 3;
    /*
    읽지 않은 알람 개수 조회
     */
    public Integer getUnreadCount(Long userId){
        // 유저 아이디 기반 읽지 않은 이력서 개수 가져오기
        return notificationRepository.countByMemberIdAndIsReadFalse(userId);
    }

    /*
    전체 알람 조회
     */
    public List<NotificationResponseDto> getRecentNotification(Long memberId){
        LocalDate notificationDate = LocalDate.now().plusDays(NOTIFICATION_DAYS_BEFORE);

        // 유저 아이디 기반으로 알림 가져오기
        List<Notification> notificationDtoList = notificationRepository.findByMemberIdOrderByCreatedAtDesc(memberId, notificationDate);

        return notificationDtoList.stream()
                .map(NotificationResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    /*
    읽은 알람 설정
     */
    @Transactional
    public NotificationResponseDto checkReadNotification(Long memberId, Long notificationId){
        // memberId, notificationId를 통해서 알림 찾기
        Notification notification = notificationRepository.findByIdAndMemberId(memberId, notificationId)
                .orElseThrow(()-> new BusinessException(ExceptionType.NOTIFICATION_NOT_FOUND));
        // 해당 알림의 상태를 읽음, 즉 true로
        notification.markAsRead();
        return NotificationResponseDto.fromEntity(notification);
    }

    /*
    알림 삭제
     */
    @Transactional
    public void deleteNotification(Long memberId, Long notificationId){
        Notification notification = notificationRepository.findByIdAndMemberId(notificationId,memberId)
                .orElseThrow(() -> new BusinessException(ExceptionType.NOTIFICATION_NOT_FOUND));
        notificationRepository.delete(notification);
    }
}
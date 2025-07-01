package kr.ac.kumoh.d138.JobForeigner.job.repository;

import kr.ac.kumoh.d138.JobForeigner.job.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    /*
    읽지 않은 알람 개수 조회
     */
    int countByMemberIdAndReadFalse(Long memberId);

    /*
    모든 알림 가져오기
     */
    List<Notification> findByMemberIdOrderByCreatedAtDesc(Long memberId);

    /*
    멤버 id, 알림 id로 가져오기
     */
    Optional<Notification> findByIdAndMemberId(Long id, Long memberId);

}
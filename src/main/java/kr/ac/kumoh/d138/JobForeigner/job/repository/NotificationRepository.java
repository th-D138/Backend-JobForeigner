package kr.ac.kumoh.d138.JobForeigner.job.repository;

import kr.ac.kumoh.d138.JobForeigner.job.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    @Query(value = "SELECT n FROM Notification n JOIN JobPost j ON n.jobPostId = j.id " +
            "WHERE n.memberId = :memberId AND DATE(j.expiryAt) = :targetDate ORDER BY n.createdAt DESC ")
    List<Notification> findByMemberIdOrderByCreatedAtDesc(@Param("memberId") Long memberId, @Param("targetDate") LocalDate targetDate);

    /*
    멤버 id, 알림 id로 가져오기
     */
    Optional<Notification> findByIdAndMemberId(Long id, Long memberId);

}
package kr.ac.kumoh.d138.JobForeigner.member.repository;

import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<Member> findByCompanyId(Long companyId);
    boolean existsByCompanyId(Long companyId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM member WHERE member_id IN (SELECT member_id FROM member WHERE deleted_at <= :threshold ORDER BY member_id LIMIT :batchSize)", nativeQuery = true)
    int hardDeleteByDeletedAtBefore(@Param("threshold") LocalDateTime threshold, @Param("batchSize") int batchSize);
}

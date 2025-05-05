package kr.ac.kumoh.d138.JobForeigner.scrap.reposiitory;

import kr.ac.kumoh.d138.JobForeigner.scrap.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    boolean existsByMemberIdAndJobPostId(Long memberId, Long jobPostId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Scrap s WHERE s.member.id = :memberId AND s.jobPost.id = :jobPostId")
    void deleteByMemberIdAndJobPostId(
            @Param("memberId") Long memberId,
            @Param("jobPostId") Long jobPostId
    );
}

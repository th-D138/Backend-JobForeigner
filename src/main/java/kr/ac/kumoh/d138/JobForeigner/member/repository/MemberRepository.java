package kr.ac.kumoh.d138.JobForeigner.member.repository;

import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.resume.dto.response.ResumeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    boolean existsByUsername(String username);

    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<Member> findByCompanyId(Long companyId);
    boolean existsByCompanyId(Long companyId);

}

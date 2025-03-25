package kr.ac.kumoh.d138.JobForeigner.member.repository;

import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}

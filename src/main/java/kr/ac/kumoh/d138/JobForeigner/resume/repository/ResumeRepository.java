package kr.ac.kumoh.d138.JobForeigner.resume.repository;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;
import kr.ac.kumoh.d138.JobForeigner.resume.dto.response.ResumeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume,Long> {
    Optional<Resume> findByIdAndMemberId(Long resumeId, Long memberId);

    Page<Resume> findAllByMemberId(Long memberId, Pageable pageable);

    List<Resume> findTop3ByMemberIdOrderByUpdatedAtAsc(Long memberId);
}

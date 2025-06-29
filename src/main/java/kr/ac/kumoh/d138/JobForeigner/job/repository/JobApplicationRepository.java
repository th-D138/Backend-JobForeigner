package kr.ac.kumoh.d138.JobForeigner.job.repository;

import kr.ac.kumoh.d138.JobForeigner.job.domain.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    boolean existsByJobPostIdAndMemberId(Long jobPostId, Long memberId);
}
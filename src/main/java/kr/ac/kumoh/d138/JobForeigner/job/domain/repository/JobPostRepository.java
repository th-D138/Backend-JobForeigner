package kr.ac.kumoh.d138.JobForeigner.job.domain.repository;

import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    JobPost findByCompanyId(Long companyId);
}

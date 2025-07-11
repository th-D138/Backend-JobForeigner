package kr.ac.kumoh.d138.JobForeigner.job.repository;

import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findAllByCompanyId(Long companyId);
}

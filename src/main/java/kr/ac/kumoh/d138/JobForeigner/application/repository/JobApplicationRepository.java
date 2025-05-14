package kr.ac.kumoh.d138.JobForeigner.application.repository;

import kr.ac.kumoh.d138.JobForeigner.application.domain.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {
}

package kr.ac.kumoh.d138.JobForeigner.rating.repository;

import kr.ac.kumoh.d138.JobForeigner.rating.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findAllByCompanyId(Long companyId);
}

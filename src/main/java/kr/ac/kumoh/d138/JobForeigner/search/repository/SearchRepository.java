package kr.ac.kumoh.d138.JobForeigner.search.repository;

import kr.ac.kumoh.d138.JobForeigner.job.domain.Company;
import kr.ac.kumoh.d138.JobForeigner.search.dto.response.MainSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchRepository {
   Page<MainSearchResponse> mainSearch(String keyword, Pageable pageable);
}

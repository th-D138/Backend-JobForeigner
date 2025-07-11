package kr.ac.kumoh.d138.JobForeigner.search.repository;

import kr.ac.kumoh.d138.JobForeigner.search.dto.response.MainSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SearchRepository {
   Page<MainSearchResponse> mainSearch(String keyword, Pageable pageable);
}

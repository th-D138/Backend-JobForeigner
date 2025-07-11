package kr.ac.kumoh.d138.JobForeigner.search.service;

import kr.ac.kumoh.d138.JobForeigner.search.dto.response.MainSearchResponse;
import kr.ac.kumoh.d138.JobForeigner.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;

    public Page<MainSearchResponse> searchAll(String keyword, Pageable pageable){
        return searchRepository.mainSearch(keyword, pageable);

    }
}

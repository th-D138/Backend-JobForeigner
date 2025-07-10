package kr.ac.kumoh.d138.JobForeigner.search.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.kumoh.d138.JobForeigner.job.domain.Company;
import kr.ac.kumoh.d138.JobForeigner.job.domain.QCompany;
import kr.ac.kumoh.d138.JobForeigner.job.domain.QJobPost;
import kr.ac.kumoh.d138.JobForeigner.job.dto.response.JobPostResponseDto;
import kr.ac.kumoh.d138.JobForeigner.search.dto.response.MainSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MainSearchResponse> mainSearch(String query, Pageable pageable) {
        QCompany company = QCompany.company;
        QJobPost jobPost = QJobPost.jobPost;
        BooleanBuilder builder = new BooleanBuilder();

        if(query != null && !query.isEmpty()){
            builder.and(company.companyName.containsIgnoreCase(query)
                    .or(jobPost.title.containsIgnoreCase(query)));
        }

        // 회사, 채용공고를 한 번에 반환
        List<Company> response = queryFactory
                .selectFrom(company)
                .leftJoin(company.jobPostList, jobPost).fetchJoin()
                .distinct()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(company.countDistinct())
                .from(company)
                .leftJoin(company.jobPostList, jobPost)
                .where(builder)
                .fetchOne();

        List<MainSearchResponse> mainSearchResponseList = response.stream().map(
                c -> new MainSearchResponse(
                        c.getId(),
                        c.getCompanyName(),
                        c.getDescription(),
                        c.getAddress(),
                        c.getEmployeeCount(),
                        c.getJobPostList().stream().map(
                                j -> new JobPostResponseDto(
                                        j.getId(),
                                        j.getTitle(),
                                        j.getDescription(),
                                        j.getLocation(),
                                        j.getEmploymentType(),
                                        j.getSalary(),
                                        j.getCareer(),
                                        j.getPublished(),
                                        j.getGrade()
                                )
                        ).toList()
                )
        ).toList();

        return new PageImpl<>(mainSearchResponseList, pageable, totalCount != null ? totalCount : 0);
    }
}

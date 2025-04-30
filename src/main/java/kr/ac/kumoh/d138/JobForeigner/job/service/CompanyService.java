package kr.ac.kumoh.d138.JobForeigner.job.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.job.domain.Company;
import kr.ac.kumoh.d138.JobForeigner.job.domain.CompanyRating;
import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPost;
import kr.ac.kumoh.d138.JobForeigner.job.domain.QCompany;
import kr.ac.kumoh.d138.JobForeigner.job.dto.*;
import kr.ac.kumoh.d138.JobForeigner.job.repository.CompanyRepository;
import kr.ac.kumoh.d138.JobForeigner.job.repository.JobPostRepository;
import kr.ac.kumoh.d138.JobForeigner.rating.Rating;
import kr.ac.kumoh.d138.JobForeigner.rating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final RatingRepository ratingRepository;
    private final JobPostRepository jobPostRepository;

    private final JPAQueryFactory queryFactory;

    public Page<CompanyResponseDto> getAllCompany(String companyName, String region, String jobType, Pageable pageable) {
        QCompany company = QCompany.company;
        BooleanBuilder builder = new BooleanBuilder();

        // 기업명 검색
        if (companyName != null && !companyName.isEmpty()){
            builder.and(company.companyName.containsIgnoreCase(companyName));
        }
        // 지역 검색
        if (region != null && !region.isEmpty()){
            builder.and(company.address.containsIgnoreCase(region));
        }
        // 직종 검색
        if (jobType != null && !jobType.isEmpty()){
            builder.and(company.category.eq(jobType));
        }
        // Content 쿼리: 검색 조건에 맞는 페이지 범위의 결과를 가져옴
        List<Company> content = queryFactory
                .selectFrom(company)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Count 쿼리: 전체 검색 조건에 맞는 총 개수를 가져옴
        Long total = queryFactory
                .select(company.count())
                .from(company)
                .where(builder)
                .fetchOne();

        // 엔티티 -> DTO 변환
        List<CompanyResponseDto> dtos = content.stream()
                .map(CompanyResponseDto::fromEntity)
                .collect(Collectors.toList());

        // total이 null일 가능성을 고려하여 0 처리
        return new PageImpl<>(dtos, pageable, total != null ? total : 0);
    }

    public CompanyDetailResponseDto getCompanyDetail(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(()->new BusinessException(ExceptionType.COMPANY_NOT_FOUND));
        JobPost jobpost=jobPostRepository.findByCompanyId(company.getId()).orElse(null);
        // 기업 정보 매핑
        CompanyInfoDto companyInfoDto=CompanyInfoDto.fromEntity(company);
        // 채용정보 매핑
        JobPostDto jobPostDto = null;
        if (jobpost != null) {
            jobPostDto = JobPostDto.fromEntity(jobpost);
        }
        // 연봉 매핑
        SalaryInfoDto salaryInfoDto=SalaryInfoDto.fromEntity(company);
        // 기업 평점 매핑
        CompanyRatingDto companyRatingDto=CompanyRatingDto.fromEntity(company);
        // 후기 매핑
        List<Rating> reviewList=ratingRepository.findAllByCompanyId(company.getId());

        List<ReviewDto> reviewDtoList=reviewList.stream()
                .map((rating)->ReviewDto.fromEntity(rating,"채주혁"))
                .toList();
        return CompanyDetailResponseDto.builder()
                .companyInfoDto(companyInfoDto)
                .salaryInfoDto(salaryInfoDto)
                .jobPostDto(jobPostDto)
                .companyRatingDto(companyRatingDto)
                .reviewDto(reviewDtoList)
                .build();
    }
    @Transactional
    public void updateCompanyRating(Company company){
        List<Rating> ratings=company.getRatings();

        if(ratings.isEmpty()){
            resetCompanyRating(company);
            return;
        }

        // 항목별 평균 점수 계산
        float avgSalary   = computeAverage(ratings, Rating::getSalarySatisfaction);
        float avgWorkLife = computeAverage(ratings, Rating::getWorkLifeBalance);
        float avgCulture  = computeAverage(ratings, Rating::getOrganizationalCulture);
        float avgWelfare  = computeAverage(ratings, Rating::getWelfare);
        float avgStability= computeAverage(ratings, Rating::getJobStability);

        // CompanyRating 엔티티에 저장
        CompanyRating cr = company.getCompanyRating();
        cr.setAverageSalarySatisfaction(avgSalary);
        cr.setAverageWorkLifeBalance(avgWorkLife);
        cr.setAverageOrganizationalCulture(avgCulture);
        cr.setAverageWelfare(avgWelfare);
        cr.setAverageJobStability(avgStability);
        cr.setTotalReviews((long) ratings.size());

        // 전체 평균 평점(레이더 차트에 활용할 수 있는 '종합 점수')
        float overallAverage = (avgSalary + avgWorkLife + avgCulture + avgWelfare + avgStability) / 5;
        company.setAverageRating(overallAverage);
    }
    @Transactional
    public void resetCompanyRating(Company company){
        CompanyRating cr=company.getCompanyRating();
        cr.setAverageSalarySatisfaction(0f);
        cr.setAverageWorkLifeBalance(0f);
        cr.setAverageOrganizationalCulture(0f);
        cr.setAverageJobStability(0f);
        cr.setAverageWelfare(0f);
        cr.setTotalReviews(0L);
        company.setAverageRating(0f);
    }
    private float computeAverage(List<Rating> ratings, Function<Rating,Float> mapper){
        float sum=0f;
        for(Rating rating:ratings)
            sum+=mapper.apply(rating);
        return sum/ratings.size();
    }
}

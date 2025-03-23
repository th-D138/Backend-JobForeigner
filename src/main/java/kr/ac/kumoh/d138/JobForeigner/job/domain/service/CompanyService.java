package kr.ac.kumoh.d138.JobForeigner.job.domain.service;

import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.mapper.CompanyMapper;
import kr.ac.kumoh.d138.JobForeigner.job.domain.Company;
import kr.ac.kumoh.d138.JobForeigner.job.domain.CompanyRating;
import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPost;
import kr.ac.kumoh.d138.JobForeigner.job.domain.dto.*;
import kr.ac.kumoh.d138.JobForeigner.job.domain.repository.CompanyRepository;
import kr.ac.kumoh.d138.JobForeigner.job.domain.repository.JobPostRepository;
import kr.ac.kumoh.d138.JobForeigner.rating.Rating;
import kr.ac.kumoh.d138.JobForeigner.rating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;
    private final RatingRepository ratingRepository;
    private final JobPostRepository jobPostRepository;

    public Page<CompanyResponseDto> getAllCompany(Pageable pageable) {
        Page<Company> companies = companyRepository.findAll(pageable);
        return companies.map(companyMapper::toDto);

    }

    public CompanyDetailResponseDto getCompanyDetail(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(()->new BusinessException(ExceptionType.NOT_FOUND_USER));
        JobPost jobpost=jobPostRepository.findByCompanyId(company.getId());
        // 기업 정보 매핑
        CompanyInfoDto companyInfoDto=CompanyInfoDto.fromEntity(company);
        // 채용정보 매핑
        JobPostDto jobPostDto=JobPostDto.fromEntity(jobpost);
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

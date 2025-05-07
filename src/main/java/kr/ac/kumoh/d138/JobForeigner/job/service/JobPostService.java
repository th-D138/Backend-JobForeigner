package kr.ac.kumoh.d138.JobForeigner.job.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.job.domain.Company;
import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPost;
import kr.ac.kumoh.d138.JobForeigner.job.domain.JobPostStatus;
import kr.ac.kumoh.d138.JobForeigner.job.domain.QCompany;
import kr.ac.kumoh.d138.JobForeigner.job.dto.company.request.JobPostRequestDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.company.request.JobTempPostRequestDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.company.response.JobPostDetailResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.company.response.JobPostResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.dto.company.response.UpdateJobPostResponseDto;
import kr.ac.kumoh.d138.JobForeigner.job.repository.CompanyRepository;
import kr.ac.kumoh.d138.JobForeigner.job.repository.JobPostRepository;
import kr.ac.kumoh.d138.JobForeigner.scrap.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.ac.kumoh.d138.JobForeigner.job.domain.QCompany.company;
import static kr.ac.kumoh.d138.JobForeigner.job.domain.QJobPost.jobPost;

@Service
@RequiredArgsConstructor
public class JobPostService {

    private final JobPostRepository jobPostRepository;
    private final CompanyRepository companyRepository;
    private final JPAQueryFactory queryFactory;
    private final ScrapRepository scrapRepository;

    @Transactional
    public void createJobPost(JobPostRequestDto jobPostRequestDto) {
        Company company = companyRepository.findById(jobPostRequestDto.getCompanyId())
                .orElseThrow(() -> new BusinessException(ExceptionType.COMPANY_NOT_FOUND));
        JobPost newJobPost = JobPost.builder()
                .title(jobPostRequestDto.getTitle())
                .description(jobPostRequestDto.getDescription())
                .location(jobPostRequestDto.getLocation())
                .employmentType(jobPostRequestDto.getEmploymentType())
                .salary(jobPostRequestDto.getSalary())
                .career(jobPostRequestDto.getCareer())
                .grade(jobPostRequestDto.getGrade())
                .published(JobPostStatus.SUBMITTED)
                .expiryAt(jobPostRequestDto.getExpiryAt())
                .company(company)
                .build();
        company.addJobPost(newJobPost); 
        jobPostRepository.save(newJobPost);
    }

    @Transactional
    public void saveTemporaryJobPost(JobTempPostRequestDto jobTempPostRequestDto){
        Company company = companyRepository.findById(jobTempPostRequestDto.getCompanyId())
                .orElseThrow(()->new BusinessException(ExceptionType.COMPANY_NOT_FOUND));
        JobPost newJobPost = JobPost.builder()
                .title(jobTempPostRequestDto.getTitle())
                .description(jobTempPostRequestDto.getDescription())
                .location(jobTempPostRequestDto.getLocation())
                .employmentType(jobTempPostRequestDto.getEmploymentType())
                .salary(jobTempPostRequestDto.getSalary())
                .career(jobTempPostRequestDto.getCareer())
                .grade(jobTempPostRequestDto.getGrade())
                .published(JobPostStatus.TEMP)
                .expiryAt(jobTempPostRequestDto.getExpiryAt())
                .company(company)
                .build();
        company.addJobPost(newJobPost);
        jobPostRepository.save(newJobPost);
    }

    public Page<JobPostResponseDto> getAllJobPost(String companyName, String region, String jobType, Pageable pageable){
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(jobPost.published.eq(JobPostStatus.SUBMITTED));
        // 기업명 검색

        if (companyName != null && !companyName.isEmpty()){
            builder.and(jobPost.company.companyName.containsIgnoreCase(companyName));
        }
        // 지역 검색
        if (region != null && !region.isEmpty()){
            builder.and(jobPost.company.address.containsIgnoreCase(region));
        }
        // 직종 검색
        if (jobType != null && !jobType.isEmpty()){
            builder.and(jobPost.company.category.eq(jobType));
        }
        // Content 쿼리: 검색 조건에 맞는 페이지 범위의 결과를 가져옴
        List<JobPost> content = queryFactory
                .selectFrom(jobPost)
                .join(jobPost.company, company)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Count 쿼리: 전체 검색 조건에 맞는 총 개수를 가져옴
        Long total = queryFactory
                .select(jobPost.count())
                .from(jobPost)
                .join(jobPost.company, company)
                .where(builder)
                .fetchOne();

        // JobPost 엔티티를 JobPostResponseDto로 변환
        List<JobPostResponseDto> dtoList = content.stream()
                .map(JobPostResponseDto::fromEntity)
                .toList();

        return new PageImpl<>(dtoList, pageable, total);
    }


    public JobPostDetailResponseDto getJobPostDetail(Long jobPostId, Long memberId){
        JobPost jp = queryFactory
                .selectFrom(jobPost)
                .join(jobPost.company, QCompany.company)
                .where(
                        jobPost.id.eq(jobPostId),                      // ID 일치
                        jobPost.published.eq(JobPostStatus.SUBMITTED)  // SUBMITTED 상태
                )
                .fetchOne();
        if (jp == null) {
            throw new BusinessException(ExceptionType.JOBPOST_NOT_FOUND);
        }
        boolean isScrapped = scrapRepository.existsByMemberIdAndJobPostId(memberId, jobPostId);
        return JobPostDetailResponseDto.fromEntity(jp, isScrapped);
    }

    @Transactional
    public UpdateJobPostResponseDto updateJobPost(Long jobPostId, JobPostRequestDto jobPostRequestDto){
        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(()->new BusinessException(ExceptionType.JOBPOST_NOT_FOUND));
        jobPost.updatePost(jobPostRequestDto);

        return UpdateJobPostResponseDto.fromEntity(jobPost);
    }

    @Transactional
    public void deleteJobPost(Long jobPostId) {
        jobPostRepository.deleteById(jobPostId);
    }
}
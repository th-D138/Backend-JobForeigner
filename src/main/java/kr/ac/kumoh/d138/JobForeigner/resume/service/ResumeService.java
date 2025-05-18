package kr.ac.kumoh.d138.JobForeigner.resume.service;

import jakarta.transaction.Transactional;
import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.member.repository.MemberRepository;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;
import kr.ac.kumoh.d138.JobForeigner.resume.dto.request.*;
import kr.ac.kumoh.d138.JobForeigner.resume.dto.response.ResumeResponse;
import kr.ac.kumoh.d138.JobForeigner.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final MemberRepository memberRepository;

    private final String UPLOAD_DIR = "";

    // TODO: 서버 컴 세팅 완료하기
    @Transactional
    public void createResume(ResumeRequest request, MultipartFile image, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MEMBER_NOT_FOUND));

        // 1. 이미지 저장
        // 서버 세팅 전까지 주석 처리
        // String imageUrl = storeImage(image);

        String imageUrl = "/images";

        // 2. Resume 생성
        Resume resume = Resume.builder()
                .member(member)
                .resumeImageUrl(imageUrl)
                .educations(
                        request.educations() != null
                                ? request.educations().stream().map(EducationRequest::toEducation).collect(Collectors.toList())
                                : new ArrayList<>())
                .employments(
                        request.employments() != null
                                ? request.employments().stream().map(EmploymentRequest::toEmployment).collect(Collectors.toList())
                                : new ArrayList<>())
                .certificates(
                        request.certificates() != null
                                ? request.certificates().stream().map(CertificatesRequest::toCertificate).collect(Collectors.toList())
                                : new ArrayList<>())
                .awards(
                        request.awards() != null
                                ? request.awards().stream().map(AwardsRequest::toAward).collect(Collectors.toList())
                                : new ArrayList<>())
                .skills(
                        request.skills() != null
                                ? request.skills().stream().map(SkillsRequest::toSkill).collect(Collectors.toList())
                                : new ArrayList<>())
                .languages(
                        request.languages() != null
                                ? request.languages().stream().map(LanguagesRequest::toLanguage).collect(Collectors.toList())
                                : new ArrayList<>())
                .portfolios(
                        request.portfolios() != null
                                ? request.portfolios().stream().map(PortfoliosRequest::toPortfolio).collect(Collectors.toList())
                                : new ArrayList<>())
                .jobPreference(
                        request.jobPreference() != null
                                ? request.jobPreference().toJobPreference()
                                : null)
                .expat(
                        request.expat() != null
                                ? request.expat().stream().map(ExpatRequest::toExpat).collect(Collectors.toList())
                                : new ArrayList<>())
                .build();


        // 양방향 연관관계 설정
        resume.updateAllRelation(resume);

        // 3. 저장
        resumeRepository.save(resume);
    }

    // 이미지 저장 후 저장된 주소를 반환
    // TODO: 이전에 저장된 이미지를 삭제하는 로직 필요
    public String storeImage(MultipartFile file) {
        try {
            // 이미지의 확장자 추출
            String ext = Optional.ofNullable(file.getOriginalFilename())
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(f.lastIndexOf(".")))
                    .orElse("");

            String savedName = UUID.randomUUID() + ext;
            Path savePath = Paths.get(UPLOAD_DIR, savedName);
            Files.createDirectories(savePath.getParent());
            file.transferTo(savePath.toFile());

            return "/images/" + savedName;
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 실패", e);
        }
    }

    public Resume getResume(Long memberId, Long resumeId) {
        return resumeRepository.findByIdAndMemberId(resumeId, memberId)
                .orElseThrow(()->new BusinessException(ExceptionType.RESUME_FORBIDDEN));
    }

    public List<ResumeResponse> getResumeSummary(Long memberId) {
        List<Resume> response = resumeRepository.findTop3ByMemberIdOrderByUpdatedAtAsc(memberId);
        return response.stream()
                .map(ResumeResponse::toResumeResponse)
                .toList();
    }

    public Page<ResumeResponse> getAllResume(Long memberId, Pageable pageable) {
        Page<Resume> response = resumeRepository.findAllByMemberId(memberId, pageable);
        return response.map(ResumeResponse::toResumeResponse);
    }

    @Transactional
    public ResumeResponse updateResume(ResumeRequest request, MultipartFile image, Long memberId, Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(()-> new BusinessException(ExceptionType.RESUME_NOT_FOUND));

        if(!resume.getMember().getId().equals(memberId)) {
            throw new BusinessException(ExceptionType.RESUME_FORBIDDEN);
        }

        String imageUrl = null;
        if(image != null && !image.isEmpty()) {
            imageUrl = storeImage(image);
        }

        log.error("입력받은 내용 : {}", request.toString());

        resume.updateResume(
                imageUrl != null ? imageUrl : resume.getResumeImageUrl(),
                request.educations() != null
                        ? request.educations().stream().map(EducationRequest::toEducation).collect(Collectors.toList())
                        : null,
                request.employments() != null
                        ? request.employments().stream().map(EmploymentRequest::toEmployment).collect(Collectors.toList())
                        : null,
                request.certificates() != null
                        ? request.certificates().stream().map(CertificatesRequest::toCertificate).collect(Collectors.toList())
                        : null,
                request.awards() != null
                        ? request.awards().stream().map(AwardsRequest::toAward).collect(Collectors.toList())
                        : null,
                request.skills() != null
                        ? request.skills().stream().map(SkillsRequest::toSkill).collect(Collectors.toList())
                        : null,
                request.languages() != null
                        ? request.languages().stream().map(LanguagesRequest::toLanguage).collect(Collectors.toList())
                        : null,
                request.portfolios() != null
                        ? request.portfolios().stream().map(PortfoliosRequest::toPortfolio).collect(Collectors.toList())
                        : null,
                request.jobPreference() != null
                        ? request.jobPreference().toJobPreference()
                        : null,
                request.expat() != null
                        ? request.expat().stream().map(ExpatRequest::toExpat).collect(Collectors.toList())
                        : null
        );

        Resume response = resumeRepository.findById(resumeId)
                .orElseThrow(()->new BusinessException(ExceptionType.RESUME_NOT_FOUND));

        return ResumeResponse.toResumeResponse(response);

    }

    public void deleteResume(Long memberId, Long resumeId) {
        resumeRepository.deleteById(resumeId);
    }
}

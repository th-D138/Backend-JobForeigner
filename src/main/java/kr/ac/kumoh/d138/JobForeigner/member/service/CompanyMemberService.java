package kr.ac.kumoh.d138.JobForeigner.member.service;

import kr.ac.kumoh.d138.JobForeigner.email.service.AuthEmailService;
import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.job.repository.CompanyRepository;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Address;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Gender;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.member.domain.MemberType;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.CompanySignUpRequest;
import kr.ac.kumoh.d138.JobForeigner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CompanyMemberService {
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

    private final AuthEmailService authEmailService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(CompanySignUpRequest req) {
        if (!companyRepository.existsById(req.companyId())) {
            throw new BusinessException(ExceptionType.COMPANY_NOT_FOUND);
        }

        if (memberRepository.existsByEmail(req.email())) {
            throw new BusinessException(ExceptionType.EMAIL_ALREADY_EXISTS);
        }
        if (memberRepository.existsByCompanyId(req.companyId())) {
            throw new BusinessException(ExceptionType.COMPANY_ALREADY_EXISTS);
        }

        Member member = Member.builder()
                .name(req.name())
                .username(req.username())
                .password(passwordEncoder.encode(req.password()))
                .type(MemberType.COMPANY)
                .companyId(req.companyId())
                .phoneNumber(req.phoneNumber())
                .email(req.email())
                .gender(Gender.valueOf(req.gender()))
                .birthDate(req.birthDate())
                .profileImageUrl(req.profileImageUrl())
                .address(new Address(req.address(), req.detailAddress(), req.zipcode()))
                .build();

        memberRepository.save(member);
        
        // 이메일 주소 인증 메일 전송
        authEmailService.sendMail(member.getEmail());
    }
}

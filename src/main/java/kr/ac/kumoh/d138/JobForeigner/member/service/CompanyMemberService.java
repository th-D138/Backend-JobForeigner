package kr.ac.kumoh.d138.JobForeigner.member.service;

import kr.ac.kumoh.d138.JobForeigner.email.service.AuthEmailService;
import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.job.repository.CompanyRepository;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Address;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Gender;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.member.domain.MemberType;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.SignUpForCompanyRequest;
import kr.ac.kumoh.d138.JobForeigner.member.repository.MemberRepository;
import kr.ac.kumoh.d138.JobForeigner.token.dto.JwtPair;
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

    private final AuthService authService;
    private final AuthEmailService authEmailService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtPair signUp(SignUpForCompanyRequest req) {
        if (!companyRepository.existsById(req.companyId())) {
            throw new BusinessException(ExceptionType.COMPANY_NOT_FOUND);
        }

        if (memberRepository.existsByUsername(req.username())) {
            throw new BusinessException(ExceptionType.USERNAME_ALREADY_EXISTS);
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
                .type(MemberType.valueOf(req.type()))
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
        authEmailService.sendMail(member.getId());

        return authService.generateJwtPair(member);
    }
}

package kr.ac.kumoh.d138.JobForeigner.member.service;

import kr.ac.kumoh.d138.JobForeigner.email.service.AuthEmailService;
import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.job.domain.Company;
import kr.ac.kumoh.d138.JobForeigner.job.repository.CompanyRepository;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Address;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Gender;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.member.domain.MemberType;
import kr.ac.kumoh.d138.JobForeigner.member.repository.MemberRepository;
import kr.ac.kumoh.d138.JobForeigner.token.dto.JwtPair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Profile({"local", "dev"})
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TestMemberService {
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

    private final AuthService authService;
    private final AuthEmailService authEmailService;

    private final PasswordEncoder passwordEncoder;

    private final String COMPANY_NAME = "국립금오공과대학교 차세대컴퓨터네트워킹연구실";
    private final String COMPANY_BUSINESS_NUMBER = "5138302865";
    private final String COMPANY_ADDRESS = "경상북도 구미시 대학로 61";
    private final String COMPANY_EMAIL = "test@kumoh.ac.kr";
    private final String COMPANY_PHONE_NUMBER = "0544780000";
    private final String COMPANY_URL = "https://ce.kumoh.ac.kr/";
    private final String COMPANY_DESCRIPTION = "Together, Tomorrow - 미래형 공학교육 선도대학이자 대한민국 유일 특성화 국립공과대학 창의인재 양성을 위한 R&BD 특화대학, 세계로의 kit! 국립금오공과대학교입니다.";
    private final int COMPANY_EMPLOYEE_COUNT = 6;
    private final String COMPANY_CATEGORY = "IT개발·데이터";
    private final String COMPANY_CEO_NAME = "홍길순";
    private final int COMPANY_AVERAGE_SALARY = 2400;
    private final int COMPANY_MONTHLY_TAKE_HOME = 1980000;

    private final String MEMBER_NAME = "홍길순";
    private final String MEMBER_PASSWORD = "test!234";
    private final String MEMBER_TYPE_COMPANY = "COMPANY";
    private final String MEMBER_TYPE_FOREIGNER = "FOREIGNER";
    private final String MEMBER_PHONE_NUMBER = "01000000000";
    private final String MEMBER_EMAIL = "test@kumoh.ac.kr";
    private final String MEMBER_GENDER = "FEMALE";
    private final int MEMBER_BIRTHDATE_YEAR = 1994;
    private final int MEMBER_BIRTHDATE_MONTH = 12;
    private final int MEMBER_BIRTHDATE_DAY = 9;
    private final String MEMBER_PROFILE_IMAGE_URL = "https://avatars.githubusercontent.com/u/193306804?s=200&v=4";
    private final String MEMBER_ADDRESS = "경상북도 구미시 대학로 61";
    private final String MEMBER_DETAIL_ADDRESS = "국립금오공과대학교 디지털관 138호 차세대컴퓨터네트워킹연구실";
    private final String MEMBER_ZIPCODE = "39177";

    @Transactional
    public JwtPair signUpForForeigner() {
        if (memberRepository.existsByEmail(MEMBER_EMAIL)) {
            throw new BusinessException(ExceptionType.EMAIL_ALREADY_EXISTS);
        }

        Member member = Member.builder()
                .name(MEMBER_NAME)
                .password(passwordEncoder.encode(MEMBER_PASSWORD))
                .type(MemberType.valueOf(MEMBER_TYPE_FOREIGNER))
                .phoneNumber(MEMBER_PHONE_NUMBER)
                .email(MEMBER_EMAIL)
                .gender(Gender.valueOf(MEMBER_GENDER))
                .birthDate(LocalDate.of(MEMBER_BIRTHDATE_YEAR, MEMBER_BIRTHDATE_MONTH, MEMBER_BIRTHDATE_DAY))
                .profileImageUrl(MEMBER_PROFILE_IMAGE_URL)
                .address(new Address(MEMBER_ADDRESS, MEMBER_DETAIL_ADDRESS, MEMBER_ZIPCODE))
                .build();
        member.setVerified();

        memberRepository.save(member);

        return authService.generateJwtPair(member);
    }

    @Transactional
    public JwtPair signUpForCompany() {
        if (memberRepository.existsByEmail(MEMBER_EMAIL)) {
            throw new BusinessException(ExceptionType.EMAIL_ALREADY_EXISTS);
        }

        Company company = new Company();
        company.setCompanyName(COMPANY_NAME);
        company.setBusinessNumber(COMPANY_BUSINESS_NUMBER);
        company.setAddress(COMPANY_ADDRESS);
        company.setEmail(COMPANY_EMAIL);
        company.setPhoneNumber(COMPANY_PHONE_NUMBER);
        company.setUrl(COMPANY_URL);
        company.setDescription(COMPANY_DESCRIPTION);
        company.setEmployeeCount(COMPANY_EMPLOYEE_COUNT);
        company.setCategory(COMPANY_CATEGORY);
        company.setCeoName(COMPANY_CEO_NAME);
        company.setAverageSalary(COMPANY_AVERAGE_SALARY);
        company.setMonthlyTakeHome(COMPANY_MONTHLY_TAKE_HOME);
        companyRepository.save(company);

        Member member = Member.builder()
                .name(MEMBER_NAME)
                .password(passwordEncoder.encode(MEMBER_PASSWORD))
                .type(MemberType.valueOf(MEMBER_TYPE_COMPANY))
                .companyId(company.getId())
                .phoneNumber(MEMBER_PHONE_NUMBER)
                .email(MEMBER_EMAIL)
                .gender(Gender.valueOf(MEMBER_GENDER))
                .birthDate(LocalDate.of(MEMBER_BIRTHDATE_YEAR, MEMBER_BIRTHDATE_MONTH, MEMBER_BIRTHDATE_DAY))
                .profileImageUrl(MEMBER_PROFILE_IMAGE_URL)
                .address(new Address(MEMBER_ADDRESS, MEMBER_DETAIL_ADDRESS, MEMBER_ZIPCODE))
                .build();
        member.setVerified();

        memberRepository.save(member);

        return authService.generateJwtPair(member);
    }
}

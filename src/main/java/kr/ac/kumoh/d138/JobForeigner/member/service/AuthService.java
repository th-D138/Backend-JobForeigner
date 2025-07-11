package kr.ac.kumoh.d138.JobForeigner.member.service;

import kr.ac.kumoh.d138.JobForeigner.email.service.AuthEmailService;
import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.JwtClaims;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.access.AccessTokenData;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.access.AccessTokenProvider;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.refresh.RefreshTokenData;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.refresh.RefreshTokenProvider;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.member.repository.MemberRepository;
import kr.ac.kumoh.d138.JobForeigner.token.dto.JwtPair;
import kr.ac.kumoh.d138.JobForeigner.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthEmailService authEmailService;

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    private final PasswordEncoder passwordEncoder;

    public JwtPair signIn(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ExceptionType.MEMBER_INFO_INVALID));

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BusinessException(ExceptionType.MEMBER_INFO_INVALID);
        }

        // 토큰 발급
        return generateJwtPair(member);
    }

    public void signOut(String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }

    @Transactional
    public void delete(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new BusinessException(ExceptionType.MEMBER_NOT_FOUND);
        }

        memberRepository.deleteById(memberId);
        refreshTokenRepository.deleteByMemberId(memberId);
    }

    public JwtPair generateJwtPair(Member member) {
        JwtClaims claims = JwtClaims.create(member);
        AccessTokenData accessToken = accessTokenProvider.createToken(claims);
        RefreshTokenData refreshToken = refreshTokenProvider.createToken(claims);

        refreshTokenRepository.save(refreshToken);

        return JwtPair.of(accessToken.token(), accessToken.expiredIn(), refreshToken.token(), refreshToken.expiredIn());
    }

    @Transactional
    public void changeEmail(Long memberId, String email) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ExceptionType.MEMBER_NOT_FOUND));

        // 이미 등록된 이메일인지 확인
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(ExceptionType.EMAIL_ALREADY_EXISTS);
        }

        // 이메일 변경 및 이메일 주소 인증 메일 발송
        member.changeEmail(email);
        authEmailService.sendMail(email);
    }
}

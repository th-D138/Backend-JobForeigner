package kr.ac.kumoh.d138.JobForeigner.member.service;

import kr.ac.kumoh.d138.JobForeigner.global.jwt.JwtClaims;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.access.AccessTokenData;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.access.AccessTokenProvider;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.refresh.RefreshTokenData;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.refresh.RefreshTokenProvider;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Address;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Gender;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.member.domain.MemberType;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.SignUpForForeignerRequest;
import kr.ac.kumoh.d138.JobForeigner.member.repository.MemberRepository;
import kr.ac.kumoh.d138.JobForeigner.token.domain.RefreshToken;
import kr.ac.kumoh.d138.JobForeigner.token.domain.RefreshTokenRepository;
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
public class ForeignerMemberService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtPair signUp(SignUpForForeignerRequest req) {
        Member member = Member.builder()
                .name(req.name())
                .username(req.username())
                .password(passwordEncoder.encode(req.password()))
                .type(MemberType.valueOf(req.type()))
                .phoneNumber(req.phoneNumber())
                .email(req.email())
                .gender(Gender.valueOf(req.gender()))
                .birthDate(req.birthDate())
                .profileImageUrl(req.profileImageUrl())
                .address(new Address(req.address(), req.detailAddress(), req.zipcode()))
                .build();

        memberRepository.save(member);

        log.info("외국인 사용자 회원가입이 완료되었습니다. {}", member);

        // 토큰 발급
        JwtClaims claims = JwtClaims.create(member);
        AccessTokenData accessToken = accessTokenProvider.createToken(claims);
        RefreshTokenData refreshToken = refreshTokenProvider.createToken(claims);

        refreshTokenRepository.save(RefreshToken.from(refreshToken));

        log.debug("외국인 사용자 회원가입 후 토큰이 발행되었습니다. {}, {}", accessToken, refreshToken);

        return JwtPair.of(accessToken.token(), accessToken.expiredIn(), refreshToken.token(), refreshToken.expiredIn());
    }

}

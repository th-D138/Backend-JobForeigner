package kr.ac.kumoh.d138.JobForeigner.member.service;

import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.JwtClaims;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.access.AccessTokenData;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.access.AccessTokenProvider;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.refresh.RefreshTokenData;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.refresh.RefreshTokenProvider;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Address;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Gender;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.member.domain.MemberType;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.MemberProfileRequest;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.ProfileImageRequest;
import kr.ac.kumoh.d138.JobForeigner.member.dto.request.SignUpRequest;
import kr.ac.kumoh.d138.JobForeigner.member.dto.response.MemberProfileResponse;
import kr.ac.kumoh.d138.JobForeigner.member.repository.MemberRepository;
import kr.ac.kumoh.d138.JobForeigner.resume.service.ResumeService;
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
public class MemberService {

    private final ResumeService resumeService;

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtPair signUp (SignUpRequest req) {
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

        // 토큰 발급
        JwtClaims claims = JwtClaims.create(member);
        AccessTokenData accessToken = accessTokenProvider.createToken(claims);
        RefreshTokenData refreshToken = refreshTokenProvider.createToken(claims);

        refreshTokenRepository.save(RefreshToken.from(refreshToken));

        return JwtPair.of(accessToken.token(), accessToken.expiredIn(), refreshToken.token(), refreshToken.expiredIn());
    }

    public JwtPair signIn (String username, String password) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ExceptionType.MEMBER_NOT_FOUND));

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BusinessException(ExceptionType.MEMBER_INFO_INVALID);
        }

        // 토큰 발급
        JwtClaims claims = JwtClaims.create(member);
        AccessTokenData accessToken = accessTokenProvider.createToken(claims);
        RefreshTokenData refreshToken = refreshTokenProvider.createToken(claims);

        refreshTokenRepository.save(RefreshToken.from(refreshToken));

        return JwtPair.of(accessToken.token(), accessToken.expiredIn(), refreshToken.token(), refreshToken.expiredIn());
    }

    public void signOut(Long memberId) {
        refreshTokenRepository.deleteById(memberId);
    }

    public MemberProfileResponse getMemberProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new BusinessException(ExceptionType.MEMBER_NOT_FOUND));
        return MemberProfileResponse.toMemberProfileResponse(member);
    }

    @Transactional
    public void updateMemberProfile(Long memberId, MemberProfileRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new BusinessException(ExceptionType.MEMBER_NOT_FOUND));
        member.updateMemberProfile(request);
    }

    @Transactional
    public void updateProfileImage(ProfileImageRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new BusinessException(ExceptionType.MEMBER_NOT_FOUND));
        String imageUrl = resumeService.storeImage(request.image());
        member.updateProfileImage(imageUrl);

    }

}

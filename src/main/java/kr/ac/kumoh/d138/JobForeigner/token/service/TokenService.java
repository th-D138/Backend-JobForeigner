package kr.ac.kumoh.d138.JobForeigner.token.service;

import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.JwtClaims;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.access.AccessTokenData;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.access.AccessTokenProvider;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.refresh.RefreshTokenData;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.refresh.RefreshTokenProvider;
import kr.ac.kumoh.d138.JobForeigner.token.domain.RefreshToken;
import kr.ac.kumoh.d138.JobForeigner.token.domain.RefreshTokenRepository;
import kr.ac.kumoh.d138.JobForeigner.token.dto.JwtPair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtPair refresh(JwtPair tokens) {
        // 만료된 액세스 토큰에서 클레임(페이로드) 추출
        JwtClaims claims = accessTokenProvider.getClaims(tokens.accessToken())
                .orElseThrow(() -> new BusinessException(ExceptionType.JWT_INVALID));

        // 요청한 액세스 토큰의 사용자의 리프레시 토큰이 서버에 존재하는지 확인
        RefreshToken savedRefreshToken = refreshTokenRepository.findById(claims.memberId())
                .orElseThrow(() -> new BusinessException(ExceptionType.JWT_NOT_EXIST));

        // 요청한 리프레시 토큰과 서버의 리프레시 토큰이 일치하는지 확인
        if (!tokens.refreshToken().equals(savedRefreshToken.getRefreshToken())) {
            throw new BusinessException(ExceptionType.JWT_INVALID);
        }

        // 서버에 저장된 토큰을 삭제
        refreshTokenRepository.delete(savedRefreshToken);

        // 새로운 액세스 토큰과 리프레시 토큰 발급
        AccessTokenData newAccessToken = accessTokenProvider.createToken(claims);
        RefreshTokenData newRefreshToken = refreshTokenProvider.createToken(claims);
        refreshTokenRepository.save(RefreshToken.from(newRefreshToken));

        return JwtPair.of(newAccessToken.token(), newAccessToken.expiredIn(), newRefreshToken.token(), newRefreshToken.expiredIn());
    }

}

package kr.ac.kumoh.d138.JobForeigner.token.repository;

import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.refresh.RefreshTokenData;

import java.util.Optional;

public interface RefreshTokenRepository {
    void save(final RefreshTokenData refreshToken);

    Optional<RefreshTokenData> findById(final String refreshToken);

    void deleteById(final String refreshToken);

    void deleteByMemberId(final Long memberId);
}

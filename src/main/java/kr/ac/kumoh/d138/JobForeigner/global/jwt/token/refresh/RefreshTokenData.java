package kr.ac.kumoh.d138.JobForeigner.global.jwt.token.refresh;

public record RefreshTokenData(
        Long memberId,
        String token,
        int expiredIn
) {
}

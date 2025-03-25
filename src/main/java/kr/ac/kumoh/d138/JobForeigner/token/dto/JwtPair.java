package kr.ac.kumoh.d138.JobForeigner.token.dto;

public record JwtPair (
        String accessToken,
        int accessTokenExpiredIn,
        String refreshToken,
        int refreshTokenExpiredIn
) {
    public static JwtPair of(String accessToken, int accessTokenExpiredIn, String refreshToken, int refreshTokenExpiredIn) {
        return new JwtPair(accessToken, accessTokenExpiredIn, refreshToken, refreshTokenExpiredIn);
    }
}

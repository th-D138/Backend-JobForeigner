package kr.ac.kumoh.d138.JobForeigner.global.jwt.token.refresh;

import kr.ac.kumoh.d138.JobForeigner.global.jwt.JwtClaims;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.properties.JwtProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class RefreshTokenProvider {

    private final JwtProperties jwtProperties;

    public RefreshTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public RefreshTokenData createToken(JwtClaims claims) {
        return new RefreshTokenData(claims.memberId(), UUID.randomUUID().toString(), jwtProperties.getRefreshTokenExpiredIn());
    }

}

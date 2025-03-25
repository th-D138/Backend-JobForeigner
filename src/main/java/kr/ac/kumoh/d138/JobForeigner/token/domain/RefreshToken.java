package kr.ac.kumoh.d138.JobForeigner.token.domain;

import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.refresh.RefreshTokenData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@RedisHash("refreshToken")
@AllArgsConstructor
@Getter
public class RefreshToken {

    @Id
    private Long memberId;

    @Indexed
    private String refreshToken;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Integer expired;

    public static RefreshToken from(RefreshTokenData data) {
        return new RefreshToken(data.memberId(), data.token(), data.expiredIn());
    }

}

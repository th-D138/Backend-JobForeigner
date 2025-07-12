package kr.ac.kumoh.d138.JobForeigner.email.repository;

import kr.ac.kumoh.d138.JobForeigner.email.domain.AuthCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class AuthCodeRepositoryImpl implements AuthCodeRepository {
    private static final String KEY_PREFIX = "authCode";

    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> valueOperations;

    private final int AUTH_CODE_EXPIRED_IN;

    public AuthCodeRepositoryImpl(RedisTemplate<String, String> redisTemplate,
                                  @Value("${job-foreigner.mail.auth.expired-in}") int authCodeExpiredIn) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();

        this.AUTH_CODE_EXPIRED_IN = authCodeExpiredIn;
    }

    @Override
    public void save(AuthCode authCode) {
        String key = getKey(authCode.getCode());
        valueOperations.set(key, authCode.getEmail());
        redisTemplate.expire(key, AUTH_CODE_EXPIRED_IN, TimeUnit.SECONDS);
    }

    @Override
    public Optional<AuthCode> findById(String code) {
        String key = getKey(code);

        String email = valueOperations.get(key);
        if (Objects.isNull(email)) {
            return Optional.empty();
        }

        return Optional.of(new AuthCode(code, email));
    }

    @Override
    public void deleteById(String code) {
        String key = getKey(code);
        redisTemplate.delete(key);
    }

    private String getKey(final String code) {
        return KEY_PREFIX + ":" + code;
    }
}

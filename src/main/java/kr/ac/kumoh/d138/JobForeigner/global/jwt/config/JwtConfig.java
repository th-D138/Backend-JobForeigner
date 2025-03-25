package kr.ac.kumoh.d138.JobForeigner.global.jwt.config;

import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.access.AccessTokenProvider;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.properties.JwtProperties;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.properties.KeyProperties;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.refresh.RefreshTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({ KeyProperties.class, JwtProperties.class })
public class JwtConfig {

    @Bean
    public AccessTokenProvider accessTokenProvider(KeyProperties keyProperties, JwtProperties jwtProperties) {
        return new AccessTokenProvider(keyProperties, jwtProperties);
    }

    @Bean
    public RefreshTokenProvider refreshTokenProvider(JwtProperties jwtProperties) {
        return new RefreshTokenProvider(jwtProperties);
    }

}

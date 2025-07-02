package kr.ac.kumoh.d138.JobForeigner.global.jwt.token;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kumoh.d138.JobForeigner.token.dto.JwtPair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String COOKIE_NAME_REFRESH_TOKEN = "refresh_token";

    @Value("${job-foreigner.jwt.domain}")
    private String domain;

    @Value("${job-foreigner.jwt.path}")
    private String path;

    @Value("${job-foreigner.jwt.secure}")
    private boolean secure;

    /**
     * AccessToken을 AUTHORIZATION 헤더에 넣고, RefreshToken을 Cookie에 넣습니다.
     */
    public void setAccessTokenAndRefreshToken(HttpServletResponse response, JwtPair tokens) {
        setAccessToken(response, tokens);
        setRefreshToken(response, tokens);
    }

    /**
     * AccessToken을 AUTHORIZATION 헤더에 넣습니다.
     */
    public void setAccessToken(HttpServletResponse response, JwtPair tokens) {
        response.addHeader(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokens.accessToken());
    }

    /**
     * RefreshToken을 Cookie에 넣습니다.
     */
    public void setRefreshToken(HttpServletResponse response, JwtPair tokens) {
        Cookie cookie = new Cookie(COOKIE_NAME_REFRESH_TOKEN, tokens.refreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(secure);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(tokens.refreshTokenExpiredIn());
        response.addCookie(cookie);
    }

    /**
     * RefreshToken을 Cookie에서 지웁니다.
     */
    public void deleteRefreshToken(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_NAME_REFRESH_TOKEN, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(secure);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

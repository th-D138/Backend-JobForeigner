package kr.ac.kumoh.d138.JobForeigner.global.jwt.token;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kumoh.d138.JobForeigner.token.dto.JwtPair;
import org.springframework.http.HttpHeaders;

public class TokenUtils {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String COOKIE_NAME_REFRESH_TOKEN = "refresh_token";

    /**
     * AccessToken을 AUTHORIZATION 헤더에 넣고, RefreshToken을 Cookie에 넣습니다.
     */
    public static void setAccessTokenAndRefreshToken(HttpServletResponse response, JwtPair tokens) {
        setAccessToken(response, tokens);
        setRefreshToken(response, tokens);
    }

    /**
     * AccessToken을 AUTHORIZATION 헤더에 넣습니다.
     */
    public static void setAccessToken(HttpServletResponse response, JwtPair tokens) {
        response.addHeader(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokens.accessToken());
    }

    /**
     * RefreshToken을 Cookie에 넣습니다.
     */
    public static void setRefreshToken(HttpServletResponse response, JwtPair tokens) {
        Cookie cookie = new Cookie(COOKIE_NAME_REFRESH_TOKEN, tokens.refreshToken());
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        cookie.setPath("/tokens/refresh");
        cookie.setMaxAge(tokens.refreshTokenExpiredIn());
        response.addCookie(cookie);
    }

    /**
     * RefreshToken을 Cookie에서 지웁니다.
     */
    public static void deleteRefreshToken(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_NAME_REFRESH_TOKEN, null);
        cookie.setMaxAge(0);
        cookie.setPath("/tokens/refresh");
        response.addCookie(cookie);
    }
}

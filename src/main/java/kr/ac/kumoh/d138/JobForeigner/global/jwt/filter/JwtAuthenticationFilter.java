package kr.ac.kumoh.d138.JobForeigner.global.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.authentication.JwtUnauthenticatedToken;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.token.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authorizationHeader == null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtUnauthenticatedToken unauthenticatedToken = new JwtUnauthenticatedToken(resolveAccessToken(request)
                .orElseThrow(() -> new BusinessException(ExceptionType.JWT_NOT_EXIST)));

        Authentication authentication = authenticationManager.authenticate(unauthenticatedToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private Optional<String> resolveAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.hasText(authorizationHeader)) {
            return Optional.empty();
        }

        if (!authorizationHeader.startsWith(TokenUtils.BEARER_PREFIX)) {
            return Optional.empty();
        }

        return Optional.of(authorizationHeader.substring(TokenUtils.BEARER_PREFIX.length()));
    }
}

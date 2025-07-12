package kr.ac.kumoh.d138.JobForeigner.global.log;

import jakarta.servlet.http.HttpServletRequest;
import kr.ac.kumoh.d138.JobForeigner.global.jwt.authentication.JwtAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class ControllerLoggingAspect {
    @Around(
            "within(kr.ac.kumoh.d138.JobForeigner..controller..*Controller) " +
                    "&& !within(io.swagger.v3.oas.annotations..*) " +
                    "&& !within(org.springdoc..*)"
    )
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestUri = request.getRequestURI();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = method.getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = null;
        if (authentication instanceof JwtAuthentication jwtAuthentication) {
            principal = jwtAuthentication.getPrincipal();
        }
        Long memberId = principal != null ? (Long) principal : 0;

        log.info("API 호출 시작 [memberId={}, requestUri={}, requestClassAndMethod={}::{}]", memberId, requestUri, className, methodName);

        Object result = joinPoint.proceed();

        log.info("API 호출 완료 [memberId={}, requestUri={}, requestClassAndMethod={}::{}]", memberId, requestUri, className, methodName);

        return result;
    }
}

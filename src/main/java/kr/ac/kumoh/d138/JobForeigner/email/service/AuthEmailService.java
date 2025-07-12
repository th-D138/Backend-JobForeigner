package kr.ac.kumoh.d138.JobForeigner.email.service;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import kr.ac.kumoh.d138.JobForeigner.email.domain.AuthCode;
import kr.ac.kumoh.d138.JobForeigner.email.dto.request.VerifyAuthCodeRequest;
import kr.ac.kumoh.d138.JobForeigner.email.repository.AuthCodeRepository;
import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Reader;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthEmailService {
    private static final String SUBJECT_AUTH_CODE = "AUTH_CODE";
    private static final int LENGTH = 6;

    private final AuthCodeRepository authCodeRepository;

    private final EmailSendService sendService;

    @Value("${job-foreigner.mail.auth.subject}")
    private String authEmailSubject;

    @Value("${job-foreigner.mail.auth.base-url}")
    private String baseUrl;

    @Transactional
    public void sendMail(String email) {
        String code = createCode();
        String subject = authEmailSubject.replace(SUBJECT_AUTH_CODE, code);
        String htmlContent = createContent(code);

        AuthCode authCode = new AuthCode(code, email);
        authCodeRepository.save(authCode);
        sendService.sendMail(email, subject, htmlContent);
    }

    private String createCode() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < LENGTH; ++i) {
                builder.append(random.nextInt(10));
            }

            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("이메일 주소 인증 메일의 인증 코드 생성에 실패했습니다: {}", e.getMessage());
            throw new BusinessException(ExceptionType.UNEXPECTED_SERVER_ERROR);
        }
    }

    private String createContent(String code) {
        Map<String, Object> model = Map.of(
                "baseUrl", baseUrl,
                "code", code
        );

        try (Reader reader = new MustacheResourceTemplateLoader("templates/", ".html")
                .getTemplate("email/auth/auth-mail")) {
            Template template = Mustache.compiler().compile(reader);
            return template.execute(model);
        } catch (Exception e) {
            log.error("템플릿 읽기에 실패하여 인증 메일이 발송되지 않습니다: {}", e.getMessage());
            throw new BusinessException(ExceptionType.UNEXPECTED_SERVER_ERROR);
        }
    }

    @Transactional
    public void verifyEmail(VerifyAuthCodeRequest request) {
        AuthCode authCode = authCodeRepository.findById(request.code())
                .orElseThrow(() -> new BusinessException(ExceptionType.AUTH_CODE_INVALID));

        if (!request.email().equals(authCode.getEmail())) {
            throw new BusinessException(ExceptionType.AUTH_CODE_INVALID);
        }

        authCodeRepository.deleteById(authCode.getCode());
    }
}

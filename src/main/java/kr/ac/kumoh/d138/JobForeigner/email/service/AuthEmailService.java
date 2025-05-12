package kr.ac.kumoh.d138.JobForeigner.email.service;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import kr.ac.kumoh.d138.JobForeigner.email.domain.AuthCode;
import kr.ac.kumoh.d138.JobForeigner.email.repository.AuthCodeRepository;
import kr.ac.kumoh.d138.JobForeigner.global.exception.BusinessException;
import kr.ac.kumoh.d138.JobForeigner.global.exception.ExceptionType;
import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Reader;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthEmailService {
    private final MemberRepository memberRepository;
    private final AuthCodeRepository authCodeRepository;

    private final EmailSendService sendService;

    @Value("${job-foreigner.local.mail.auth.subject}")
    private String authEmailSubject;

    @Value("${job-foreigner.local.mail.auth.base-url}")
    private String baseUrl;

    @Value("${job-foreigner.local.mail.auth.verify-path}")
    private String verifyPath;

    @Transactional
    public void sendMail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ExceptionType.MEMBER_NOT_FOUND));

        // 이미 이메일 인증을 받았다면 새로운 인증 코드를 받을 수 없도록 함
        if (member.isVerified()) {
            throw new BusinessException(ExceptionType.EMAIL_ALREADY_VERIFIED);
        }

        String code = UUID.randomUUID().toString();

        // 인증 코드가 데이터베이스에 이미 생성돼 있다면 갱신하고, 없다면 새롭게 만듦
        authCodeRepository.findByEmail(member.getEmail())
                .ifPresentOrElse(entity -> entity.reissue(code), () -> {
                    AuthCode entity = new AuthCode(member.getEmail(), code);
                    authCodeRepository.save(entity);
                });

        String htmlContent = createContent(member.getName(), code);
        sendService.sendMail(member.getEmail(), authEmailSubject, htmlContent);
    }

    private String createContent(String name, String code) {
        Map<String, Object> model = Map.of(
                "name", name,
                "baseUrl", baseUrl,
                "verifyUrl", baseUrl + verifyPath + code
        );

        try (Reader reader = new MustacheResourceTemplateLoader("templates/", ".html")
                .getTemplate("email/auth/auth-mail")) {
            Template template = Mustache.compiler().compile(reader);
            return template.execute(model);
        } catch (Exception e) {
            log.error("템플릿 읽기에 실패하여 {}님께 인증 메일이 발송되지 않습니다: {}", name, e.getMessage());
            throw new BusinessException(ExceptionType.UNEXPECTED_SERVER_ERROR);
        }
    }

    @Transactional
    public void verifyEmail(String code) {
        AuthCode authCode = authCodeRepository.findByCode(code)
                .orElseThrow(() -> new BusinessException(ExceptionType.AUTH_CODE_INVALID));

        // 인증 링크를 누른 일자가 만료 일자 이후라면 인증을 수행할 수 없도록 함
        if (LocalDateTime.now().isAfter(authCode.getExpiredAt())) {
            throw new BusinessException(ExceptionType.AUTH_CODE_INVALID);
        }

        Member member = memberRepository.findByEmail(authCode.getEmail())
                .orElseThrow(() -> new BusinessException(ExceptionType.MEMBER_NOT_FOUND));
        member.setVerified();

        authCodeRepository.deleteById(authCode.getId());
    }
}

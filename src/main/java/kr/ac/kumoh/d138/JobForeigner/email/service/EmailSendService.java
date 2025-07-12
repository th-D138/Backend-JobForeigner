package kr.ac.kumoh.d138.JobForeigner.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final JavaMailSender mailSender;

    @Async
    public void sendMail(String email, String subject, String content) {
        try {
            MimeMessage mimeMessage = createMimeMessage(email, subject, content);
            mailSender.send(mimeMessage);
        } catch (AddressException e) {
            log.warn("이메일 주소가 유효하지 않음: {}", email);
        } catch (MessagingException e) {
            log.warn("이메일 전송 중 SMTP 서버 오류 발생: {}", e.getMessage());
        } catch (Exception e) {
            log.error("이메일 전송 중 예상치 못한 오류 발생: {}", e.getMessage());
        }
    }

    private MimeMessage createMimeMessage(String email, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setTo(new InternetAddress(email, true));
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);

        return mimeMessage;
    }
}

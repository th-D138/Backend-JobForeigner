package kr.ac.kumoh.d138.JobForeigner.email.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "auth_code")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "expired_at", nullable = false) // TODO: expired_at이 경과된 레코드를 삭제하는 작업 필요
    private LocalDateTime expiredAt;

    public AuthCode(String email, String code) {
        this.email = email;
        this.code = code;
        this.expiredAt = LocalDateTime.now().plusHours(24L);
    }

    public void reissue(String code) {
        this.code = code;
        this.expiredAt = LocalDateTime.now().plusHours(24L);
    }
}

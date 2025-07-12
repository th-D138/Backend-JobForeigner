package kr.ac.kumoh.d138.JobForeigner.email.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthCode {
    private String code;
    private String email;

    public AuthCode(String code, String email) {
        this.code = code;
        this.email = email;
    }
}

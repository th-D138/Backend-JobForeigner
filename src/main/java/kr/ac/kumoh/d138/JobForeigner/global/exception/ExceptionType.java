package kr.ac.kumoh.d138.JobForeigner.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    // Common
    UNEXPECTED_SERVER_ERROR(INTERNAL_SERVER_ERROR,"C001","예상치 못한 에러 발생"),
    BINDING_ERROR(BAD_REQUEST,"C002","바인딩시 에러 발생"),
    ESSENTIAL_FIELD_MISSING_ERROR(NO_CONTENT , "C003","필수적인 필드 부재"),

    // User
    NOT_FOUND_USER(NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(UNAUTHORIZED, "U002", "비밀번호가 일치하지 않습니다."),

    // Security
    NEED_AUTHORIZED(UNAUTHORIZED, "S001", "토큰이 포함되어 있지 않습니다."),
    ACCESS_DENIED(FORBIDDEN, "S002", "접근 권한이 없습니다."),
    JWT_EXPIRED(UNAUTHORIZED, "S003", "토큰이 만료되었습니다."),
    JWT_INVALID(UNAUTHORIZED, "S004", "토큰이 잘못되었습니다."),
    JWT_NOT_EXIST(UNAUTHORIZED, "S005", "토큰이 존재하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}

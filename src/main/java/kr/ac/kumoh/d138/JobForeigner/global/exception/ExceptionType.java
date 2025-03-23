package kr.ac.kumoh.d138.JobForeigner.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    //common
    UNEXPECTED_SERVER_ERROR(INTERNAL_SERVER_ERROR,"C001","예상치 못한 에러 발생"),
    BINDING_ERROR(BAD_REQUEST,"C002","바인딩시 에러 발생"),
    ESSENTIAL_FIELD_MISSING_ERROR(NO_CONTENT , "C003","필수적인 필드 부재"),

    //User
    NOT_FOUND_USER(NOT_FOUND,"U001","해당하는 유저를 찾을 수 없습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
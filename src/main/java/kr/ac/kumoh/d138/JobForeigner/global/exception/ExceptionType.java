package kr.ac.kumoh.d138.JobForeigner.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    // Common
    UNEXPECTED_SERVER_ERROR(INTERNAL_SERVER_ERROR,"C001","예상치 못한 서버 오류가 발생했습니다."),
    BINDING_ERROR(BAD_REQUEST,"C002","요청 데이터 변환 과정에서 오류가 발생했습니다."),
    ESSENTIAL_FIELD_MISSING_ERROR(NO_CONTENT , "C003","필수 필드를 누락했습니다."),
    INVALID_ENDPOINT(NOT_FOUND, "C004", "잘못된 API URI로 요청했습니다."),
    INVALID_HTTP_METHOD(METHOD_NOT_ALLOWED, "C005","잘못된 HTTP 메서드로 요청했습니다."),

    // Member
    MEMBER_NOT_FOUND(NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),
    MEMBER_INFO_INVALID(UNAUTHORIZED, "U002", "아이디 또는 비밀번호가 일치하지 않습니다."),
    USERNAME_ALREADY_EXISTS(CONFLICT, "U003", "이미 등록되어 있거나 사용할 수 없는 아이디입니다."),
    EMAIL_ALREADY_EXISTS(CONFLICT, "U004", "이미 등록되어 있거나 사용할 수 없는 이메일 주소입니다."),
    COMPANY_ALREADY_EXISTS(CONFLICT, "U005", "이미 담당자 정보가 등록된 회사입니다."),
    EMAIL_VERIFICATION_REQUIRED(UNAUTHORIZED, "U006", "이메일 주소 인증이 필요합니다."),
    EMAIL_ALREADY_VERIFIED(CONFLICT, "U007", "이미 인증된 이메일 주소입니다."),
    AUTH_CODE_INVALID(CONFLICT, "U008", "만료되거나 잘못된 인증 코드입니다."),
    BUSINESS_NUMBER_INVALID(CONFLICT, "U009", "사업자등록번호가 잘못되었습니다."),

    // Company
    COMPANY_NOT_FOUND(NOT_FOUND,"COM001", "회사를 찾을 수 없습니다."),

    // JobPost
    JOBPOST_NOT_FOUND(NOT_FOUND,"J001","채용 공고를 찾을 수 없습니다."),

    // Security
    NEED_AUTHORIZED(UNAUTHORIZED, "S001", "인증이 필요합니다."),
    ACCESS_DENIED(FORBIDDEN, "S002", "접근 권한이 없습니다."),
    JWT_EXPIRED(UNAUTHORIZED, "S003", "인증 정보가 만료되었습니다."),
    JWT_INVALID(UNAUTHORIZED, "S004", "인증 정보가 잘못되었습니다."),
    JWT_NOT_EXIST(UNAUTHORIZED, "S005", "인증 정보가 존재하지 않습니다."),

    // Resume
    RESUME_FORBIDDEN(FORBIDDEN,"R001","접근할 수 없는 이력서입니다."),
    RESUME_NOT_FOUND(NOT_FOUND, "R002", "존재하지 않는 이력서입니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}

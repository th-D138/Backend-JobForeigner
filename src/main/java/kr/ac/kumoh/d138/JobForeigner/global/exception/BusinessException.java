package kr.ac.kumoh.d138.JobForeigner.global.exception;

import lombok.Getter;
@Getter
public class BusinessException extends RuntimeException{

    private final ExceptionType exceptionType;

    public BusinessException(ExceptionType exceptionType){
        super(exceptionType.getMessage());
        this.exceptionType=exceptionType;
    }
}
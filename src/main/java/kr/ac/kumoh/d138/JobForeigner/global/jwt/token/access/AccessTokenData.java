package kr.ac.kumoh.d138.JobForeigner.global.jwt.token.access;

public record AccessTokenData(
        String token,
        int expiredIn
){
}

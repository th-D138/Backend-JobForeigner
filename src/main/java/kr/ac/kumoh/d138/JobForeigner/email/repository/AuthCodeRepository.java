package kr.ac.kumoh.d138.JobForeigner.email.repository;

import kr.ac.kumoh.d138.JobForeigner.email.domain.AuthCode;

import java.util.Optional;

public interface AuthCodeRepository {
    void save(final AuthCode authCode);

    Optional<AuthCode> findById(final String authCode);

    void deleteById(final String authCode);
}

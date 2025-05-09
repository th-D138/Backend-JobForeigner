package kr.ac.kumoh.d138.JobForeigner.email.repository;

import kr.ac.kumoh.d138.JobForeigner.email.domain.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {
    Optional<AuthCode> findByEmail(String email);
    Optional<AuthCode> findByCode(String code);
}

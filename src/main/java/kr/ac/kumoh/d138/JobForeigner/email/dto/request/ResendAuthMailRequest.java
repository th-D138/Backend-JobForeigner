package kr.ac.kumoh.d138.JobForeigner.email.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ResendAuthMailRequest(
    @NotBlank String email
) {
}

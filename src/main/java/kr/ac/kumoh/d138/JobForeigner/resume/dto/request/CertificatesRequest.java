package kr.ac.kumoh.d138.JobForeigner.resume.dto.request;


import kr.ac.kumoh.d138.JobForeigner.resume.domain.Certificate;

import java.time.LocalDate;

public record CertificatesRequest(
        String certificateName,
        String organization,
        LocalDate date
) {
    public Certificate toCertificate(){
        return Certificate.builder()
                .certificateName(certificateName)
                .organization(organization)
                .date(date)
                .build();
    }
}

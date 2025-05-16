package kr.ac.kumoh.d138.JobForeigner.resume.dto.response;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Certificate;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;

import java.time.LocalDateTime;
import java.util.List;

public record CertificatesResponse(
        String certificateName,
        String organization,
        LocalDateTime date
) {
    public static CertificatesResponse toCertificatesResponse(Certificate certificate){
        return new CertificatesResponse(
                certificate.getCertificateName(),
                certificate.getOrganization(),
                certificate.getDate()
        );
    }

    public static List<CertificatesResponse> toCertificatesResponseList(Resume resume){
        return resume.getCertificates().stream()
                .map(CertificatesResponse::toCertificatesResponse)
                .toList();
    }

}

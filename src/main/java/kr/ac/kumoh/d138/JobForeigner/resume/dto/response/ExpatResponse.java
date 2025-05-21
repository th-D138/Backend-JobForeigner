package kr.ac.kumoh.d138.JobForeigner.resume.dto.response;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Expat;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;

import java.time.LocalDateTime;
import java.util.List;

public record ExpatResponse(
        String country,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String experience
) {
    public static ExpatResponse toExpatResponse(Expat expat) {
        return new ExpatResponse(
                expat.getCountry(),
                expat.getStartDate(),
                expat.getEndDate(),
                expat.getExperience()
        );
    }

    public static List<ExpatResponse> toExpatResponseList(Resume resume) {
        return resume.getExpat().stream()
                .map(ExpatResponse::toExpatResponse)
                .toList();
    }
}

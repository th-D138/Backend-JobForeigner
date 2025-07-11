package kr.ac.kumoh.d138.JobForeigner.resume.dto.response;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Award;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record AwardsResponse(
    String awardName,
    String organization,
    LocalDate awardYear,
    String details
) {

    public static AwardsResponse toAwardResponse(Award award) {
        return new AwardsResponse(
                award.getAwardName(),
                award.getOrganization(),
                award.getAwardYear(),
                award.getDetails()
        );
    }

    public static List<AwardsResponse> toAwardsResponseList(Resume resume) {
        return resume.getAwards().stream()
                .map(AwardsResponse::toAwardResponse)
                .toList();
    }
}

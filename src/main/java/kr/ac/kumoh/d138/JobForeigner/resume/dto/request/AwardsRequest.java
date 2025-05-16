package kr.ac.kumoh.d138.JobForeigner.resume.dto.request;


import kr.ac.kumoh.d138.JobForeigner.resume.domain.Award;

import java.time.LocalDateTime;

public record AwardsRequest(
    String awardName,
    String organization,
    LocalDateTime year,
    String details
) {
    public Award toAward(){
        return Award.builder()
                .awardName(awardName)
                .organization(organization)
                .year(year)
                .details(details)
                .build();
    }
}

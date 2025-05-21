package kr.ac.kumoh.d138.JobForeigner.resume.dto.request;


import kr.ac.kumoh.d138.JobForeigner.resume.domain.Expat;

import java.time.LocalDateTime;

public record ExpatRequest(
        String country,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String experience
) {
    public Expat toExpat(){
        return Expat.builder()
                .country(country)
                .startDate(startDate)
                .endDate(endDate)
                .experience(experience)
                .build();
    }
}

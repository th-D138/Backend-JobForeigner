package kr.ac.kumoh.d138.JobForeigner.scrap.dto;

public record ScrapResponse(
        Long jobPostId,
        boolean isScraped,
        String message
) {}

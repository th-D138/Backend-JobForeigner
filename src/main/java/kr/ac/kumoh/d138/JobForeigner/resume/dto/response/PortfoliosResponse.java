package kr.ac.kumoh.d138.JobForeigner.resume.dto.response;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Portfolio;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;

import java.util.List;

public record PortfoliosResponse(
        String portfolioUrl
) {
    public static PortfoliosResponse toPortfolioResponse(Portfolio portfolio) {
        return new PortfoliosResponse(
                portfolio.getPortfolioUrl()
        );
    }

    public static List<PortfoliosResponse> toPortfoliosResponseList(Resume resume) {
        return resume.getPortfolios().stream()
                .map(PortfoliosResponse::toPortfolioResponse)
                .toList();
    }
}

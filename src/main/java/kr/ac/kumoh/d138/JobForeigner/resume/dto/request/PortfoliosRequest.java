package kr.ac.kumoh.d138.JobForeigner.resume.dto.request;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Portfolio;

public record PortfoliosRequest(
        String portfolioUrl
) {
    public Portfolio toPortfolio(){
        return Portfolio.builder()
                .portfolioUrl(portfolioUrl)
                .build();
    }
}

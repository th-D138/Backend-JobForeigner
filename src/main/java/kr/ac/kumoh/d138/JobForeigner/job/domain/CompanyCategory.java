package kr.ac.kumoh.d138.JobForeigner.job.domain;

import lombok.Getter;

@Getter
public enum CompanyCategory {
    MANUFACTURING("제조업"),
    IT("IT"),
    BIOTECH("바이오"),
    FINANCE_INSURANCE("금융/보험"),
    CONSTRUCTION_REAL_ESTATE("건설/부동산"),
    RETAIL_TRADE("유통/무역"),
    EDUCATION_RESEARCH("교육/연구"),
    HEALTHCARE_MEDICAL("의료/헬스케어"),
    MEDIA_ENTERTAINMENT("미디어/엔터테인먼트"),
    SERVICE_INDUSTRY("서비스업"),
    ENERGY_ENVIRONMENT("에너지/환경"),
    TRANSPORTATION_LOGISTICS("운송/물류"),
    AGRICULTURE_FOOD("농업/식품"),
    FASHION_BEAUTY("패션/뷰티"),
    TELECOM_NETWORK("통신/네트워크"),
    AUTOMOTIVE_MOBILITY("자동차/모빌리티"),
    GAMING_SOFTWARE("게임/소프트웨어"),
    STARTUP("스타트업"),
    PUBLIC_SECTOR("공공기관"),
    OTHER("기타");

    private final String description;

    CompanyCategory(String description) {
        this.description = description;
    }

}
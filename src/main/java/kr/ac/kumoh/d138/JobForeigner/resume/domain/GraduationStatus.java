package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GraduationStatus {
    GRADUATED("졸업"),
    COMPLETED("수료"),
    STUDYING("재학 중");

    private final String key;
}

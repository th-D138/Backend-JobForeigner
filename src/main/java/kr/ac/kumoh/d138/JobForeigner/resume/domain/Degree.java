package kr.ac.kumoh.d138.JobForeigner.resume.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Degree {
    BACHELOR("학사"),
    MASTER("석사"),
    DOCTOR("박사");

    private final String key;
}

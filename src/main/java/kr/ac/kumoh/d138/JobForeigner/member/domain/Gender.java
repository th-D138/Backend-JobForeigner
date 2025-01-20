package kr.ac.kumoh.d138.JobForeigner.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Gender {

    MALE("MAIL"),
    FEMALE("FEMALE");

    private final String key;

}

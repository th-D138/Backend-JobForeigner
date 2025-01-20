package kr.ac.kumoh.d138.JobForeigner.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MemberType {

    FOREIGNER("ROLE_FOREIGNER", "외국인 사용자"),
    COMPANY("ROLE_COMPANY", "기업 사용자"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;

}

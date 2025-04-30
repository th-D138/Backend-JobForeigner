package kr.ac.kumoh.d138.JobForeigner.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Gender {
    MALE("MALE", 0),
    FEMALE("FEMALE", 1);

    private final String key;
    private final Integer value;

    public static Gender getGender(final Integer value) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("성별에 대한 인자는 0(남성), 또는 1(여성) 중 하나여야 합니다."));
    }
}

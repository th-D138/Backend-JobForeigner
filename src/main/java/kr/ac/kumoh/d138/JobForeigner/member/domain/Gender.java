package kr.ac.kumoh.d138.JobForeigner.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Gender {
    MALE("MALE", 'M'),
    FEMALE("FEMALE", 'F');

    private final String key;
    private final Character value;

    public static Gender getGender(final Character value) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("성별에 대한 인자는 'M'(남성), 또는 'F'(여성) 중 하나여야 합니다."));
    }
}

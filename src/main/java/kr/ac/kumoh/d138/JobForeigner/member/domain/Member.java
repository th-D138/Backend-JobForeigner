package kr.ac.kumoh.d138.JobForeigner.member.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private MemberType role;

    private String countryCode;

    private String phoneNumber;

    private String email;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    @Embedded
    private Address address;

}

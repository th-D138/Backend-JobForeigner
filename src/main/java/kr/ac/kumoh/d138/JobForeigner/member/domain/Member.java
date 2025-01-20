package kr.ac.kumoh.d138.JobForeigner.member.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    public Member(String name, String username, String password, MemberType role, String countryCode, String phoneNumber, String email, Gender gender, LocalDate birthDate, Address address) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.countryCode = countryCode;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
    }

}

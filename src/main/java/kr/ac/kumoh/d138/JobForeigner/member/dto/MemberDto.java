package kr.ac.kumoh.d138.JobForeigner.member.dto;

import kr.ac.kumoh.d138.JobForeigner.member.domain.Gender;
import kr.ac.kumoh.d138.JobForeigner.member.domain.MemberType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberDto {
    private String name;
    private String username;
    private MemberType type;
    private String countryCode;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private LocalDate birthDate;
    private String profile_image_url;
}

package kr.ac.kumoh.d138.JobForeigner.application.dto;

import kr.ac.kumoh.d138.JobForeigner.member.domain.Member;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.*;
import lombok.Getter;

@Getter
public class ResumeResponseDto {
    private Education education;
    private Employment employment;
    private Certificate certificate;
    private Award award;
    private Skill skill;
    private Language language;
    private Portfolio portfolio;
    private JobPreference jobPreference;
    private Expat expat;
    private Member member;

    public ResumeResponseDto(){

    }

}

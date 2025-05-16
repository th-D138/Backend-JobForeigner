package kr.ac.kumoh.d138.JobForeigner.resume.dto.request;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Skill;

public record SkillsRequest (
        String skill_name
){
    public Skill toSkill(){
        return Skill.builder()
                .skillName(skill_name)
                .build();
    }
}

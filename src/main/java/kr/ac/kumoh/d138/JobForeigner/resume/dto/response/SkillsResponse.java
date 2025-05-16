package kr.ac.kumoh.d138.JobForeigner.resume.dto.response;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Skill;

import java.util.List;

public record SkillsResponse(
        String skill_name
){
    public static SkillsResponse toSkillsResponse(Skill skill) {
        return new SkillsResponse(
                skill.getSkillName()
        );
    }

    public static List<SkillsResponse> toSkillsResponseList(Resume resume) {
        return resume.getSkills().stream()
                .map(SkillsResponse::toSkillsResponse)
                .toList();
    }
}

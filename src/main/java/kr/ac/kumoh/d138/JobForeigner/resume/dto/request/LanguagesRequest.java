package kr.ac.kumoh.d138.JobForeigner.resume.dto.request;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Language;

public record LanguagesRequest(
        String languages,
        String proficiency
) {
    public Language toLanguage(){
        return Language.builder()
                .languages(languages)
                .proficiency(proficiency)
                .build();
    }
}

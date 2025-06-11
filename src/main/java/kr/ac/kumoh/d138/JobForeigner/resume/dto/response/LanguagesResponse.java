package kr.ac.kumoh.d138.JobForeigner.resume.dto.response;

import kr.ac.kumoh.d138.JobForeigner.resume.domain.Language;
import kr.ac.kumoh.d138.JobForeigner.resume.domain.Resume;

import java.util.List;

public record LanguagesResponse(
        String languages,
        String proficiency
) {
    public static LanguagesResponse toLanguagesResponse(Language language) {
        return new LanguagesResponse(
                language.getLanguages(),
                language.getProficiency()
        );
    }

    public static List<LanguagesResponse> toLanguagesResponseList(Resume resume) {
        return resume.getLanguages().stream()
                .map(LanguagesResponse::toLanguagesResponse)
                .toList();
    }
}

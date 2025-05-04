package kr.ac.kumoh.d138.JobForeigner.member.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, Character> {
    @Override
    public Character convertToDatabaseColumn(Gender attribute) {
        return attribute.getValue();
    }

    @Override
    public Gender convertToEntityAttribute(Character dbData) {
        return dbData != null ? Gender.getGender(dbData) : null;
    }
}

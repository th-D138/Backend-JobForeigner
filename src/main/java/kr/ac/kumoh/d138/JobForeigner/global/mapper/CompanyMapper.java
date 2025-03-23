package kr.ac.kumoh.d138.JobForeigner.global.mapper;

import kr.ac.kumoh.d138.JobForeigner.job.domain.Company;
import kr.ac.kumoh.d138.JobForeigner.job.domain.dto.CompanyResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface CompanyMapper {
    @Mapping(source="id",target="companyId")
    CompanyResponseDto toDto(Company company);
}

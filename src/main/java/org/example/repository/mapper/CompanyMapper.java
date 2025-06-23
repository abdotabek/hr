package org.example.repository.mapper;

import org.example.dto.company.CompanyDTO;
import org.example.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CompanyMapper extends BaseMapper<CompanyDTO, Company> {

    @Mapping(source = "address.street", target = "addressDTO.street")
    @Mapping(source = "address.regionId", target = "addressDTO.regionId")
    @Mapping(source = "address.districtId", target = "addressDTO.districtId")
    CompanyDTO toDTO(Company company);
}

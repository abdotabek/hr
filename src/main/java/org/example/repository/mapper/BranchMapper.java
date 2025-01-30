package org.example.repository.mapper;


import org.example.dto.branch.BranchDTO;
import org.example.entity.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface BranchMapper extends BaseMapper<BranchDTO, Branch> {

    @Mapping(source = "company.id", target = "companyId")

    @Mapping(source = "address.street", target = "addressDTO.street")

    @Mapping(source = "address.district.id", target = "addressDTO.districtId")

    @Mapping(source = "address.region.id", target = "addressDTO.regionId")

    BranchDTO toDTO(Branch branch);

}

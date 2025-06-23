package org.example.repository.mapper;


import org.example.dto.base.CommonDTO;
import org.example.entity.District;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface DistrictMapper extends BaseMapper<CommonDTO, District> {

    CommonDTO toDTO(District district);
}

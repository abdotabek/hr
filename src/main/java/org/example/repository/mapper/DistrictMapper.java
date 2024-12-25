package org.example.repository.mapper;


import org.example.dto.district.DistrictDTO;
import org.example.entity.District;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface DistrictMapper extends BaseMapper<DistrictDTO, District> {
    DistrictDTO toDTO(District district);
}

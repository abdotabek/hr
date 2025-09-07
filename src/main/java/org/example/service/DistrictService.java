package org.example.service;

import org.example.dto.ListResult;
import org.example.dto.base.CommonDTO;
import org.example.dto.district.DistrictDetailDTO;
import org.example.dto.filter.DistrictFilterDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DistrictService {

    @Transactional
    Long create(final CommonDTO districtDTO);

    DistrictDetailDTO get(final Long id);

    List<CommonDTO> getList();

    @Transactional
    Long update(final Long id, final CommonDTO districtDTO);

    @Transactional
    void delete(final Long id);

    ListResult<CommonDTO> filterDistrict(final DistrictFilterDTO search);

    ListResult<CommonDTO> filterDistrictBySpecification(final DistrictFilterDTO search);
}

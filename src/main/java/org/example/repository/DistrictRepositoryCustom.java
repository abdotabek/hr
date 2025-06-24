package org.example.repository;

import org.example.dto.base.CommonDTO;
import org.example.dto.filter.DistrictFilterDTO;
import org.springframework.data.domain.Page;

public interface DistrictRepositoryCustom {
    Page<CommonDTO> filterDistrict(DistrictFilterDTO search);

    Page<CommonDTO> filterDistrictBySpecification(DistrictFilterDTO search);
}

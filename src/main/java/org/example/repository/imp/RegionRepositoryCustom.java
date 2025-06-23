package org.example.repository.imp;

import org.example.dto.base.CommonDTO;
import org.example.dto.filter.RegionFilterDTO;
import org.springframework.data.domain.Page;

public interface RegionRepositoryCustom {
    Page<CommonDTO> filterRegion(RegionFilterDTO search);

    Page<CommonDTO> filterRegionBySpecification(RegionFilterDTO search);
}

package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.ListResult;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.RegionFilterDTO;

import java.util.List;

public interface RegionService {

    @Transactional
    Long create(final CommonDTO regionDTO);

    CommonDTO get(final Long id);

    List<CommonDTO> getList();

    @Transactional
    Long update(final Long id, final CommonDTO regionDTO);

    @Transactional
    void delete(final Long id);

    ListResult<CommonDTO> filterRegion(final RegionFilterDTO search);

    ListResult<CommonDTO> filterRegionBySpecification(final RegionFilterDTO search);

}

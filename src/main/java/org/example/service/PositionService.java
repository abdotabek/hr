package org.example.service;

import org.example.dto.ListResult;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.PositionFilterDTO;
import org.example.dto.position.PositionDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PositionService {

    @Transactional
    Long create(final PositionDTO positionDTO);

    CommonDTO get(final Long id);

    List<PositionDTO> getList();

    Long update(final Long id, final CommonDTO positionDTO);

    void delete(final Long id);

    ListResult<CommonDTO> filterPosition(final PositionFilterDTO search);

    ListResult<CommonDTO> filterPositionBySpecification(final PositionFilterDTO search);
}

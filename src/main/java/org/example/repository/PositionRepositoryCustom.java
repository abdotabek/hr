package org.example.repository;

import org.example.dto.base.CommonDTO;
import org.example.dto.filter.PositionFilterDTO;
import org.springframework.data.domain.Page;

public interface PositionRepositoryCustom {
    Page<CommonDTO> filterPosition(PositionFilterDTO search);

    Page<CommonDTO> filterPositionBySpecification(PositionFilterDTO search);
}

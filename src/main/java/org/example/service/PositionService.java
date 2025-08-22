package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ListResult;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.PositionFilterDTO;
import org.example.dto.position.PositionDTO;
import org.example.entity.Position;
import org.example.exception.ExceptionUtil;
import org.example.repository.PositionRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    @Transactional
    public Long create(final PositionDTO positionDTO) {
        if (positionDTO.getName() == null || positionDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("position name in required");
        }
        final Position position = new Position();
        position.setName(positionDTO.getName());
        position.setLogType(positionDTO.getLogType());
        return positionRepository.save(position).getId();
    }

    public CommonDTO get(final Long id) {
        return positionRepository.findById(id)
            .map(position -> {
                final CommonDTO positionDTO = new CommonDTO();
                positionDTO.setId(position.getId());
                positionDTO.setName(position.getName());
                return positionDTO;
            }).orElseThrow(() ->
                ExceptionUtil.throwNotFoundException("position with this ID does not exist"));
    }

    public List<PositionDTO> getList() {
        return positionRepository.findAll()
            .stream().map(position -> {
                final PositionDTO positionDTO = new PositionDTO();
                positionDTO.setId(position.getId());
                positionDTO.setName(position.getName());
                positionDTO.setLogType(position.getLogType());
                return positionDTO;
            }).toList();
    }

    @Transactional
    public Long update(final Long id, final CommonDTO positionDTO) {
        if (positionDTO.getName() == null || positionDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("position name is required");
        }
        return positionRepository.findById(id)
            .map(position -> {
                position.setName(positionDTO.getName());
                positionRepository.save(position);
                return position.getId();
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("position with this ID does not exist"));
    }

    @Transactional
    public void delete(final Long id) {
        positionRepository.findById(id);
    }

    public ListResult<CommonDTO> filterPosition(final PositionFilterDTO search) {
        Page<CommonDTO> page = positionRepository.filterPosition(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

    public ListResult<CommonDTO> filterPositionBySpecification(final PositionFilterDTO search) {
        Page<CommonDTO> page = positionRepository.filterPositionBySpecification(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }
}

package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.PositionFilterDTO;
import org.example.entity.Position;
import org.example.exception.ExceptionUtil;
import org.example.repository.PositionRepository;
import org.example.service.custom.PositionCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PositionService {

    PositionRepository positionRepository;
    PositionCustomRepository positionCustomRepository;

    @Transactional
    public Long create(CommonDTO positionDTO) {
        if (positionDTO.getName() == null || positionDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("position name in required");
        }
        Position position = new Position();
        position.setName(positionDTO.getName());
        return positionRepository.save(position).getId();
    }

    public CommonDTO get(Long id) {
        return positionRepository.findById(id)
                .map(position -> {
                    CommonDTO positionDTO = new CommonDTO();
                    positionDTO.setId(position.getId());
                    positionDTO.setName(position.getName());
                    return positionDTO;
                }).orElseThrow(() ->
                        ExceptionUtil.throwNotFoundException("position with this ID does not exist"));
    }

    public List<CommonDTO> getList() {
        return positionRepository.findAll()
                .stream().map(position -> {
                    CommonDTO positionDTO = new CommonDTO();
                    positionDTO.setId(position.getId());
                    positionDTO.setName(position.getName());
                    return positionDTO;
                }).toList();
    }

    @Transactional
    public Long update(Long id, CommonDTO positionDTO) {
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
    public void delete(Long id) {
        positionRepository.findById(id);
    }

    public Page<CommonDTO> filterPosition(PositionFilterDTO search) {
        return positionCustomRepository.filterPosition(search);
    }

    public Page<CommonDTO> filterPositionBySpecification(PositionFilterDTO search) {
        return positionCustomRepository.filterPositionBySpecification(search);
    }
}

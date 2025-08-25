package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.redis.BlockListDTO;
import org.example.entity.redis.BlackList;
import org.example.exception.ExceptionUtil;
import org.example.repository.BlockListRepository;
import org.example.service.BlockListService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BlockListServiceImpl implements BlockListService {

    private final BlockListRepository blockListRepository;

    @Override
    public BlockListDTO get(final Long id) {
        return blockListRepository.findById(id)
            .map(this::toDTO).orElseThrow(
                () -> ExceptionUtil.throwNotFoundException("BlockList entry with ID does not exist."));
    }

    @Override
    public List<BlockListDTO> getList() {
        List<BlackList> blackList = (List<BlackList>) blockListRepository.findAll();
        return blackList.stream()
            .filter(Objects::nonNull)      //дает разрешения и на null значении
            .map(this::toDTO).toList();
    }

    private BlockListDTO toDTO(final BlackList blackList) {
        final BlockListDTO blockListDTO = new BlockListDTO();
        blockListDTO.setId(blackList.getId());
        return blockListDTO;
    }
}

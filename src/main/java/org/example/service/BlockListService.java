package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.redis.BlockListDTO;
import org.example.entity.redis.BlackList;
import org.example.exception.ExceptionUtil;
import org.example.repository.BlockListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BlockListService {

    BlockListRepository blockListRepository;

    public BlockListDTO get(Long id) {
        BlockListDTO blockListDTO = blockListRepository.findById(id).map(this::toDTO).orElse(null);
        if (blockListDTO == null) {
            throw ExceptionUtil.throwNotFoundException("BlockList entry with ID does not exist.");
        }
        return blockListDTO;
    }

    public List<BlockListDTO> getList() {
        List<BlackList> blackList = (List<BlackList>) blockListRepository.findAll();
        return blackList.stream()
                .filter(Objects::nonNull)      //дает разрешения и на null значении
                .map(this::toDTO).toList();
    }

    private BlockListDTO toDTO(BlackList blackList) {
        BlockListDTO blockListDTO = new BlockListDTO();
        blockListDTO.setId(blackList.getId());
        return blockListDTO;
    }
}

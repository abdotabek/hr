package org.example.service;

import org.example.dto.redis.BlockListDTO;

import java.util.List;

public interface BlockListService {

    BlockListDTO get(final Long id);

    List<BlockListDTO> getList();
}

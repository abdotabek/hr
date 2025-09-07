package org.example.service;

import org.example.dto.redis.TokenStoreDTO;

import java.util.List;

public interface TokenStoreService {

    TokenStoreDTO get(final String id);

    List<TokenStoreDTO> getList();
}

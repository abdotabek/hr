package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.TokenStoreDTO;
import org.example.entity.redis.TokenStore;
import org.example.repository.TokenStoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TokenStoreService {
    TokenStoreRepository tokenStoreRepository;

    public List<TokenStoreDTO> getEmployeeTokens(Long id) {
        return (List<TokenStoreDTO>) tokenStoreRepository.findByEmployeeId(id);
    }


    private TokenStoreDTO toDTO(TokenStore tokenStore) {
        TokenStoreDTO tokenStoreDTO = new TokenStoreDTO();
        tokenStoreDTO.setId(tokenStore.getId());
        tokenStoreDTO.setToken(tokenStore.getToken());
        tokenStoreDTO.setEmployeeId(tokenStore.getEmployeeId());
        return tokenStoreDTO;
    }

}

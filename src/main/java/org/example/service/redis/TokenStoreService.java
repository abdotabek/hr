package org.example.service.redis;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.TokenStoreDTO;
import org.example.entity.redis.TokenStore;
import org.example.repository.TokenStoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TokenStoreService {
    TokenStoreRepository tokenStoreRepository;

    public TokenStoreDTO create(TokenStoreDTO tokenStoreDTO) {
        TokenStore tokenStore = new TokenStore();
        tokenStore.setId(tokenStoreDTO.getId());
        tokenStore.setToken(tokenStoreDTO.getToken());
        tokenStore.setEmployeeId(tokenStoreDTO.getEmployeeId());
        tokenStore.setActive(tokenStoreDTO.getActive());
        return this.toDTO(tokenStoreRepository.save(tokenStore));
    }

    @Transactional
    public void deactivateToken(Long id) {
        List<TokenStore> tokenStoreList = tokenStoreRepository.findAllByEmployeeId(id);
        for (TokenStore token : tokenStoreList) {
            token.setActive(false);
            tokenStoreRepository.save(token);
        }
    }


    private TokenStoreDTO toDTO(TokenStore tokenStore) {
        TokenStoreDTO tokenStoreDTO = new TokenStoreDTO();
        tokenStoreDTO.setId(tokenStore.getId());
        tokenStoreDTO.setEmployeeId(tokenStore.getEmployeeId());
        tokenStoreDTO.setToken(tokenStore.getToken());
        tokenStoreDTO.setActive(tokenStore.getActive());
        return tokenStoreDTO;
    }
}

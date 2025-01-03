package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.TokenStoreDTO;
import org.example.entity.redis.TokenStore;
import org.example.exception.ExceptionUtil;
import org.example.repository.TokenStoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TokenStoreService {
    TokenStoreRepository tokenStoreRepository;

    public TokenStoreDTO get(String id) {
        return tokenStoreRepository.findById(id).map(this::toDTO).orElseThrow(() -> ExceptionUtil.throwNotFoundException("token with this id does not exist"));
    }

    public List<TokenStoreDTO> getList() {
        List<TokenStore> tokenStoreList = (List<TokenStore>) tokenStoreRepository.findAll();
        if (tokenStoreList.isEmpty()) {
            throw ExceptionUtil.throwNotFoundException("no tokens found");
        }
        return tokenStoreList.stream().map(this::toDTO).toList();
    }

    private TokenStoreDTO toDTO(TokenStore tokenStore) {
        TokenStoreDTO tokenStoreDTO = new TokenStoreDTO();
        tokenStoreDTO.setId(tokenStore.getId());
//        tokenStoreDTO.setToken(tokenStore.getToken());
        tokenStoreDTO.setAccessToken(tokenStore.getAccessToken());
        tokenStoreDTO.setRefreshToken(tokenStore.getRefreshToken());
        tokenStoreDTO.setEmployeeId(tokenStore.getEmployeeId());
        return tokenStoreDTO;
    }

}

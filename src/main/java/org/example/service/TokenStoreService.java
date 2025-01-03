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
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TokenStoreService {

    TokenStoreRepository tokenStoreRepository;

    public TokenStoreDTO get(String id) {
        TokenStoreDTO token = tokenStoreRepository.findById(id).map(this::toDTO).orElse(null);
        if (token == null) {
            throw ExceptionUtil.throwNotFoundException("token with this id does not exist");
        }
        return token;
    }

    public List<TokenStoreDTO> getList() {
        List<TokenStore> tokenStoreList = (List<TokenStore>) tokenStoreRepository.findAll();
        if (tokenStoreList.isEmpty()) {
            throw ExceptionUtil.throwNotFoundException("no tokens found");
        }
        return tokenStoreList.stream()
                .filter(Objects::nonNull)      //дает разрешения и на null значении
                .map(this::toDTO).toList();
    }

    private TokenStoreDTO toDTO(TokenStore tokenStore) {
        TokenStoreDTO tokenStoreDTO = new TokenStoreDTO();
        tokenStoreDTO.setId(tokenStore.getId());
        tokenStoreDTO.setAccessToken(tokenStore.getAccessToken());
        tokenStoreDTO.setRefreshToken(tokenStore.getRefreshToken());
        tokenStoreDTO.setEmployeeId(tokenStore.getEmployeeId());
        return tokenStoreDTO;
    }

}

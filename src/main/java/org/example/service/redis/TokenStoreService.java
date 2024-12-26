package org.example.service.redis;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.entity.redis.TokenStore;
import org.example.exception.ExceptionUtil;
import org.example.repository.TokenStoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TokenStoreService {
    TokenStoreRepository tokenStoreRepository;

    public TokenStore create(String id, String userId, String token, boolean active) {
        TokenStore tokenStore = new TokenStore(id, userId, token, active);
        return tokenStoreRepository.save(tokenStore);
    }

    public Optional<TokenStore> getTokenById(String id) {
        return tokenStoreRepository.findById(id);
    }

    public TokenStore update(String id, boolean active) {
        tokenStoreRepository.findById(id)
                .map(tokenStore -> {
                    tokenStore.setActive(active);
                    return tokenStoreRepository.save(tokenStore);
                }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("token this id does not exist!"));
        return new TokenStore(id, active);
    }

    public void delete(String id) {
        tokenStoreRepository.deleteById(id);
    }

    public List<TokenStore> getList() {
        return (List<TokenStore>) tokenStoreRepository.findAll();
    }
}

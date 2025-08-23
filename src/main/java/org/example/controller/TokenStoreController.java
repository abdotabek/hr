package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.api.TokenStoreControllerApi;
import org.example.dto.Result;
import org.example.dto.redis.TokenStoreDTO;
import org.example.service.TokenStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/token-stores")
@RequiredArgsConstructor
public class TokenStoreController implements TokenStoreControllerApi {

    private final TokenStoreService tokenStoreService;

    @Override
    public ResponseEntity<Result<TokenStoreDTO>> get(@PathVariable("id") final String id) {
        return ResponseEntity.ok(Result.success(tokenStoreService.get(id)));
    }

    @Override
    public ResponseEntity<Result<List<TokenStoreDTO>>> getAllTokens() {
        return ResponseEntity.ok(Result.success(tokenStoreService.getList()));
    }
}

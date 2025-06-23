package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.Result;
import org.example.dto.redis.TokenStoreDTO;
import org.example.service.TokenStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/token-stores")
@RequiredArgsConstructor
public class TokenStoreController {

    private final TokenStoreService tokenStoreService;

    @GetMapping("/{id}")
    public ResponseEntity<Result<TokenStoreDTO>> get(@PathVariable("id") final String id) {
        return ResponseEntity.ok(Result.success(tokenStoreService.get(id)));
    }

    @GetMapping
    public ResponseEntity<Result<List<TokenStoreDTO>>> getAllTokens() {
        return ResponseEntity.ok(Result.success(tokenStoreService.getList()));
    }
}

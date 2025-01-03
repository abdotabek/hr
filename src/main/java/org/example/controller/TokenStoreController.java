package org.example.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.TokenStoreDTO;
import org.example.entity.redis.TokenStore;
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
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TokenStoreController {
    TokenStoreService tokenStoreService;

    @GetMapping("/{id}")
    public ResponseEntity<TokenStoreDTO> getTokenById(@PathVariable String id) {
        try {
            TokenStoreDTO token = tokenStoreService.get(id);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TokenStoreDTO>> getAllTokens() {
        try {
            List<TokenStoreDTO> tokens = tokenStoreService.getList();
            return ResponseEntity.ok(tokens);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build();
        }
    }
}

package org.example.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.entity.redis.TokenStore;
import org.example.service.redis.TokenStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/token-store")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TokenStoreController {
    TokenStoreService tokenStoreService;

    @PostMapping
    public ResponseEntity<TokenStore> create(@RequestParam String id, @RequestParam String userId,
                                             @RequestParam String token, @RequestParam boolean active) {
        return ResponseEntity.ok(tokenStoreService.create(id, userId, token, active));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TokenStore>> get(@PathVariable String id) {
        return ResponseEntity.ok(tokenStoreService.getTokenById(id));
    }

    @GetMapping
    public ResponseEntity<List<TokenStore>> getList() {
        return ResponseEntity.ok(tokenStoreService.getList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TokenStore> update(@PathVariable("id") String id, @RequestParam boolean active) {
        return ResponseEntity.ok(tokenStoreService.update(id, active));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        tokenStoreService.delete(id);
        return ResponseEntity.ok().build();
    }
}

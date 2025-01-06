package org.example.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.TokenStoreDTO;
import org.example.repository.TokenStoreRepository;
import org.example.service.TokenStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/token-stores")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TokenStoreController {

    TokenStoreService tokenStoreService;
    TokenStoreRepository tokenStoreRepository;

    @GetMapping("/{id}")
    public ResponseEntity<TokenStoreDTO> get(@PathVariable("id") String id) {
        return ResponseEntity.ok(tokenStoreService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<TokenStoreDTO>> getAllTokens() {
        return ResponseEntity.ok(tokenStoreService.getList());
    }

  /*  @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToken(@PathVariable("id") String id) {
        tokenStoreService.delete(id);
        return ResponseEntity.ok().build();
    }*/


   /* @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByEmployeeId(@PathVariable("id") Long id) {
        tokenStoreRepository.deleteByEmployeeId(id);
        return ResponseEntity.ok().build();
    }*/
}

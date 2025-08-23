package org.example.controller.api;

import org.example.dto.Result;
import org.example.dto.redis.TokenStoreDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/token-stores")
public interface TokenStoreControllerApi {

    @GetMapping("/{id}")
    ResponseEntity<Result<TokenStoreDTO>> get(@PathVariable("id") final String id);

    @GetMapping
    ResponseEntity<Result<List<TokenStoreDTO>>> getAllTokens();
}

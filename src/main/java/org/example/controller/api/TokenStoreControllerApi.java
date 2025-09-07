package org.example.controller.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.config.doc.DocMethodAuth;
import org.example.dto.Result;
import org.example.dto.redis.TokenStoreDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/token-stores")
public interface TokenStoreControllerApi {

    @DocMethodAuth(
        summary = "Get token by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = TokenStoreDTO.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<Result<TokenStoreDTO>> get(@PathVariable("id") final String id);

    @DocMethodAuth(
        summary = "Get all tokens",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = TokenStoreDTO.class))
    )
    @GetMapping
    ResponseEntity<Result<List<TokenStoreDTO>>> getAllTokens();
}

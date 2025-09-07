package org.example.controller.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.config.doc.DocMethodAuth;
import org.example.dto.Result;
import org.example.dto.redis.BlockListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequestMapping("/api/block-lists")
public interface BlockListControllerApi {

    @DocMethodAuth(
        summary = "Get block list",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = BlockListDTO.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<Result<BlockListDTO>> get(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Get all tokens",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = BlockListDTO.class))
    )
    @GetMapping
    ResponseEntity<Result<List<BlockListDTO>>> getAllTokens();
}

package org.example.controller.api;

import org.example.dto.Result;
import org.example.dto.redis.BlockListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;


@RequestMapping("/api/block-lists")
public interface BlockListControllerApi {

    @GetMapping("/{id}")
    ResponseEntity<Result<BlockListDTO>> get(@PathVariable("id") final Long id);

    @GetMapping
    ResponseEntity<Result<List<BlockListDTO>>> getAllTokens();
}

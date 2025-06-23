package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.Result;
import org.example.dto.redis.BlockListDTO;
import org.example.service.BlockListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/block-lists")
@RequiredArgsConstructor
public class BlockListController {

    private final BlockListService blockListService;

    @GetMapping("/{id}")
    public ResponseEntity<Result<BlockListDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(blockListService.get(id)));
    }

    @GetMapping
    public ResponseEntity<Result<List<BlockListDTO>>> getAllTokens() {
        return ResponseEntity.ok(Result.success(blockListService.getList()));
    }

}

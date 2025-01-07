package org.example.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BlockListController {

    BlockListService blockListService;

    @GetMapping("/{id}")
    public ResponseEntity<BlockListDTO> get(@PathVariable("id") String id) {
        return ResponseEntity.ok(blockListService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<BlockListDTO>> getAllTokens() {
        return ResponseEntity.ok(blockListService.getList());
    }

}

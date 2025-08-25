package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.api.BlockListControllerApi;
import org.example.dto.Result;
import org.example.dto.redis.BlockListDTO;
import org.example.service.impl.BlockListServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlockListController implements BlockListControllerApi {

    private final BlockListServiceImpl blockListServiceImpl;

    @Override
    public ResponseEntity<Result<BlockListDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(blockListServiceImpl.get(id)));
    }

    @Override
    public ResponseEntity<Result<List<BlockListDTO>>> getAllTokens() {
        return ResponseEntity.ok(Result.success(blockListServiceImpl.getList()));
    }

}

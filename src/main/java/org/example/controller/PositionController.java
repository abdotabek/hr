package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.PositionFilterDTO;
import org.example.service.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    public ResponseEntity<Result<Long>> create(@RequestBody final CommonDTO positionDTO) {
        return ResponseEntity.ok(Result.success(positionService.create(positionDTO)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<CommonDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(positionService.get(id)));
    }

    @GetMapping
    public ResponseEntity<Result<List<CommonDTO>>> getList() {
        return ResponseEntity.ok(Result.success(positionService.getList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO positionDTO) {
        return ResponseEntity.ok(Result.success(positionService.update(id, positionDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        positionService.delete(id);
        return ResponseEntity.ok(Result.success());
    }

    @GetMapping("/filterPosition")
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterPosition(@RequestBody final PositionFilterDTO search) {
        return ResponseEntity.ok(Result.success(positionService.filterPosition(search)));
    }

    @GetMapping("/filterPositionBySpecification")
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterPositionBySpecification(@RequestBody final PositionFilterDTO search) {
        return ResponseEntity.ok(Result.success(positionService.filterPositionBySpecification(search)));
    }

}

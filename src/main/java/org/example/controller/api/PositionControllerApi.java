package org.example.controller.api;

import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.PositionFilterDTO;
import org.example.dto.position.PositionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/positions")
public interface PositionControllerApi {

    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final PositionDTO positionDTO);

    @GetMapping("/{id}")
    ResponseEntity<Result<CommonDTO>> get(@PathVariable("id") final Long id);

    @GetMapping
    ResponseEntity<Result<List<PositionDTO>>> getList();

    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO positionDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id);

    @GetMapping("/filterPosition")
    ResponseEntity<Result<ListResult<CommonDTO>>> filterPosition(@RequestBody final PositionFilterDTO search);

    @GetMapping("/filterPositionBySpecification")
    ResponseEntity<Result<ListResult<CommonDTO>>> filterPositionBySpecification(@RequestBody final PositionFilterDTO search);
}

package org.example.controller.api;

import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.RegionFilterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/regions")
public interface RegionControllerApi {

    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final CommonDTO regionDTO);

    @GetMapping("/{id}")
    ResponseEntity<Result<CommonDTO>> get(@PathVariable("id") final Long id);

    @GetMapping
    ResponseEntity<Result<List<CommonDTO>>> getList();

    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO regionDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id);

    @GetMapping("/filterRegion")
    ResponseEntity<Result<ListResult<CommonDTO>>> filterRegion(@RequestBody final RegionFilterDTO search);

    @GetMapping("/filterRegionBySpecification")
    ResponseEntity<Result<ListResult<CommonDTO>>> filterRegionBySpecification(@RequestBody final RegionFilterDTO search);
}

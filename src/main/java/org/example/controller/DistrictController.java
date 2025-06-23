package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.district.DistrictDetailDTO;
import org.example.dto.filter.DistrictFilterDTO;
import org.example.service.DistrictService;
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
@RequestMapping("/api/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @PostMapping
    public ResponseEntity<Result<Long>> create(@RequestBody final CommonDTO districtDTO) {
        return ResponseEntity.ok(Result.success(districtService.create(districtDTO)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<DistrictDetailDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(districtService.get(id)));
    }

    @GetMapping
    public ResponseEntity<Result<List<CommonDTO>>> getList() {
        return ResponseEntity.ok(Result.success(districtService.getList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO districtDTO) {
        return ResponseEntity.ok(Result.success(districtService.update(id, districtDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        districtService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filterDistrict")
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterDistrict(@RequestBody final DistrictFilterDTO search) {
        return ResponseEntity.ok(Result.success(districtService.filterDistrict(search)));
    }

    @GetMapping("/filterDistrictBySpecification")
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterDistrictBySpecification(@RequestBody final DistrictFilterDTO search) {
        return ResponseEntity.ok(Result.success(districtService.filterDistrictBySpecification(search)));
    }

}

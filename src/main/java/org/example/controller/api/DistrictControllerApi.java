package org.example.controller.api;

import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.district.DistrictDetailDTO;
import org.example.dto.filter.DistrictFilterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/districts")
public interface DistrictControllerApi {

    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final CommonDTO districtDTO);

    @GetMapping("/{id}")
    ResponseEntity<Result<DistrictDetailDTO>> get(@PathVariable("id") final Long id);

    @GetMapping
    ResponseEntity<Result<List<CommonDTO>>> getList();

    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO districtDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id);

    @GetMapping("/filterDistrict")
    ResponseEntity<Result<ListResult<CommonDTO>>> filterDistrict(@RequestBody final DistrictFilterDTO search);

    @GetMapping("/filterDistrictBySpecification")
    ResponseEntity<Result<ListResult<CommonDTO>>> filterDistrictBySpecification(@RequestBody final DistrictFilterDTO search);
}

package org.example.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.base.CommonDTO;
import org.example.dto.district.DistrictDTO;
import org.example.dto.district.DistrictDetailDTO;
import org.example.dto.filter.DistrictFilterDTO;
import org.example.service.DistrictService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DistrictController {
    DistrictService districtService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody DistrictDTO districtDTO) {
        return ResponseEntity.ok(districtService.create(districtDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistrictDetailDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(districtService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<CommonDTO>> getList() {
        return ResponseEntity.ok(districtService.getList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable("id") Long id, @RequestBody DistrictDTO districtDTO) {
        return ResponseEntity.ok(districtService.update(id, districtDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        districtService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filterDistrict")
    public ResponseEntity<Page<DistrictDTO>> filterDistrict(@RequestBody DistrictFilterDTO search) {
        return ResponseEntity.ok(districtService.filterDistrict(search));
    }

    @GetMapping("/filterDistrictBySpecification")
    public ResponseEntity<Page<DistrictDTO>> filterDistrictBySpecification(@RequestBody DistrictFilterDTO search) {
        return ResponseEntity.ok(districtService.filterDistrictBySpecification(search));
    }

}

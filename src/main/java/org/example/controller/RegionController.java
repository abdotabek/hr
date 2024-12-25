package org.example.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.RegionFilterDTO;
import org.example.service.RegionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RegionController {
    RegionService regionService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CommonDTO regionDTO) {
        return ResponseEntity.ok(regionService.create(regionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(regionService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<CommonDTO>> getList() {
        return ResponseEntity.ok(regionService.getList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable("id") Long id, @RequestBody CommonDTO regionDTO) {
        return ResponseEntity.ok(regionService.update(id, regionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        regionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filterRegion")
    public ResponseEntity<Page<CommonDTO>> filterRegion(@RequestBody RegionFilterDTO search) {
        return ResponseEntity.ok(regionService.filterRegion(search));
    }

    @GetMapping("/filterRegionBySpecification")
    public ResponseEntity<Page<CommonDTO>> filterRegionBySpecification(@RequestBody RegionFilterDTO search) {
        return ResponseEntity.ok(regionService.filterRegionBySpecification(search));
    }
}

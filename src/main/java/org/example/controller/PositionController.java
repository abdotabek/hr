package org.example.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.PositionFilterDTO;
import org.example.service.PositionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PositionController {
    PositionService positionService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CommonDTO positionDTO) {
        return ResponseEntity.ok(positionService.create(positionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(positionService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<CommonDTO>> getList() {
        return ResponseEntity.ok(positionService.getList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable("id") Long id, @RequestBody CommonDTO positionDTO) {
        return ResponseEntity.ok(positionService.update(id, positionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        positionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filterPosition")
    public ResponseEntity<Page<CommonDTO>> filterPosition(@RequestBody PositionFilterDTO search) {
        return ResponseEntity.ok(positionService.filterPosition(search));
    }

    @GetMapping("/filterPositionBySpecification")
    public ResponseEntity<Page<CommonDTO>> filterPositionBySpecification(@RequestBody PositionFilterDTO search) {
        return ResponseEntity.ok(positionService.filterPositionBySpecification(search));
    }

}

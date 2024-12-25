package org.example.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.base.CommonDTO;
import org.example.dto.deparment.DepartmentDTO;
import org.example.dto.deparment.DepartmentDetailDTO;
import org.example.dto.deparment.DepartmentIdNameBranchIdDTO;
import org.example.dto.filter.DepartmentFilterDTO;
import org.example.service.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DepartmentController {
    DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.create(departmentDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDetailDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(departmentService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<CommonDTO>> getList() {
        return ResponseEntity.ok(departmentService.getList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable("id") Long id, @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.update(id, departmentDTO));
    }

    @GetMapping("/departments-id-name")
    public ResponseEntity<List<DepartmentIdNameBranchIdDTO>> getDepartmentIdName() {
        return ResponseEntity.ok(departmentService.getDepartmentsIdName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        departmentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filterDepartment")
    public ResponseEntity<Page<DepartmentDTO>> filterDepartment(@RequestBody DepartmentFilterDTO search) {
        return ResponseEntity.ok(departmentService.filterDepartment(search));
    }

    @GetMapping("/filterDepartmentBySpecification")
    public ResponseEntity<Page<DepartmentDTO>> filterDepartmentBySpecification(@RequestBody DepartmentFilterDTO search) {
        return ResponseEntity.ok(departmentService.filterDepartmentBySpecification(search));
    }


}

package org.example.controller.api;

import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.deparment.DepartmentDetailDTO;
import org.example.dto.deparment.DepartmentIdNameBranchIdDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public interface DepartmentControllerApi {

    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final CommonDTO departmentDTO);

    @GetMapping("/{id}")
    ResponseEntity<Result<DepartmentDetailDTO>> get(@PathVariable("id") final Long id);

    @GetMapping
    ResponseEntity<Result<List<CommonDTO>>> getList();

    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO departmentDTO);

    @GetMapping("/departments-id-name")
    ResponseEntity<Result<List<DepartmentIdNameBranchIdDTO>>> getDepartmentIdName();
}

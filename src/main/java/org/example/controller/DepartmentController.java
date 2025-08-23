package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.controller.api.DepartmentControllerApi;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.deparment.DepartmentDetailDTO;
import org.example.dto.deparment.DepartmentIdNameBranchIdDTO;
import org.example.dto.filter.DepartmentFilterDTO;
import org.example.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController implements DepartmentControllerApi {

    private final DepartmentService departmentService;

    @Override
    public ResponseEntity<Result<Long>> create(@RequestBody final CommonDTO departmentDTO) {
        return ResponseEntity.ok(Result.success(departmentService.create(departmentDTO)));
    }

    @Override
    public ResponseEntity<Result<DepartmentDetailDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(departmentService.get(id)));
    }

    @Override
    public ResponseEntity<Result<List<CommonDTO>>> getList() {
        return ResponseEntity.ok(Result.success(departmentService.getList()));
    }

    @Override
    public ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO departmentDTO) {
        return ResponseEntity.ok(Result.success(departmentService.update(id, departmentDTO)));
    }

    @Override
    public ResponseEntity<Result<List<DepartmentIdNameBranchIdDTO>>> getDepartmentIdName() {
        return ResponseEntity.ok(Result.success(departmentService.getDepartmentsIdName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        departmentService.delete(id);
        return ResponseEntity.ok(Result.success());
    }

    @GetMapping("/filterDepartment")
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterDepartment(@RequestBody final DepartmentFilterDTO search) {
        return ResponseEntity.ok(Result.success(departmentService.filterDepartment(search)));
    }

    @GetMapping("/filterDepartmentBySpecification")
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterDepartmentBySpecification(@RequestBody final DepartmentFilterDTO search) {
        return ResponseEntity.ok(Result.success(departmentService.filterDepartmentBySpecification(search)));
    }
}

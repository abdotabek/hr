package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.controller.api.DepartmentControllerApi;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.deparment.DepartmentDetailDTO;
import org.example.dto.deparment.DepartmentIdNameBranchIdDTO;
import org.example.dto.filter.DepartmentFilterDTO;
import org.example.service.impl.DepartmentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController implements DepartmentControllerApi {

    private final DepartmentServiceImpl departmentServiceImpl;

    @Override
    public ResponseEntity<Result<Long>> create(@RequestBody final CommonDTO departmentDTO) {
        return ResponseEntity.ok(Result.success(departmentServiceImpl.create(departmentDTO)));
    }

    @Override
    public ResponseEntity<Result<DepartmentDetailDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(departmentServiceImpl.get(id)));
    }

    @Override
    public ResponseEntity<Result<List<CommonDTO>>> getList() {
        return ResponseEntity.ok(Result.success(departmentServiceImpl.getList()));
    }

    @Override
    public ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO departmentDTO) {
        return ResponseEntity.ok(Result.success(departmentServiceImpl.update(id, departmentDTO)));
    }

    @Override
    public ResponseEntity<Result<List<DepartmentIdNameBranchIdDTO>>> getDepartmentIdName() {
        return ResponseEntity.ok(Result.success(departmentServiceImpl.getDepartmentsIdName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        departmentServiceImpl.delete(id);
        return ResponseEntity.ok(Result.success());
    }

    @GetMapping("/filterDepartment")
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterDepartment(@RequestBody final DepartmentFilterDTO search) {
        return ResponseEntity.ok(Result.success(departmentServiceImpl.filterDepartment(search)));
    }

    @GetMapping("/filterDepartmentBySpecification")
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterDepartmentBySpecification(@RequestBody final DepartmentFilterDTO search) {
        return ResponseEntity.ok(Result.success(departmentServiceImpl.filterDepartmentBySpecification(search)));
    }
}

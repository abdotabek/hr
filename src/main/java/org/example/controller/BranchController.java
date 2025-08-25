package org.example.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.controller.api.BranchControllerApi;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.branch.BranchDTO;
import org.example.dto.branch.BranchDetailsDTO;
import org.example.dto.branch.BranchIdNameCompanyDTO;
import org.example.dto.filter.BranchFilterDTO;
import org.example.service.impl.BranchServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BranchController implements BranchControllerApi {

    private final BranchServiceImpl branchServiceImpl;

    @Override
    public ResponseEntity<Result<Long>> create(@RequestBody @Valid final BranchDTO branchDTO) {
        return ResponseEntity.ok(Result.success(branchServiceImpl.create(branchDTO)));
    }

    @Override
    public ResponseEntity<Result<BranchDetailsDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(branchServiceImpl.get(id)));
    }

    @Override
    public ResponseEntity<Result<List<CommonDTO>>> getList() {
        return ResponseEntity.ok(Result.success(branchServiceImpl.getList()));
    }

    @Override
    public ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody @Valid final BranchDTO branchDTO) {
        return ResponseEntity.ok(Result.success(branchServiceImpl.update(id, branchDTO)));
    }

    @Override
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        branchServiceImpl.delete(id);
        return ResponseEntity.ok(Result.success());
    }

    @Override
    public ResponseEntity<Result<List<BranchDTO>>> getAllBranches() {
        return ResponseEntity.ok(Result.success(branchServiceImpl.getAllBranches()));
    }

    @Override
    public ResponseEntity<Result<List<BranchDTO>>> getBranchesByName(@PathVariable("name") final String name) {
        return ResponseEntity.ok(Result.success(branchServiceImpl.getBranchesByName(name)));
    }

    @Override
    public ResponseEntity<Result<List<BranchDTO>>> getBranchesByCompany(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(branchServiceImpl.getBranchesByCompanyId(id)));
    }

    @Override
    public ResponseEntity<Result<Long>> getBranchCountByCompanyId(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(branchServiceImpl.getBranchCountByCompanyId(id)));
    }

    @Override
    public ResponseEntity<Result<List<String>>> getAllBranchNames() {
        return ResponseEntity.ok(Result.success(branchServiceImpl.getAllBranchNames()));
    }

    @Override
    public ResponseEntity<Result<List<BranchIdNameCompanyDTO>>> getBranchList() {
        return ResponseEntity.ok(Result.success(branchServiceImpl.getBranchList()));
    }

    @Override
    public ResponseEntity<Result<ListResult<BranchDTO>>> filterBranch(@RequestBody final BranchFilterDTO branchFilterDTO) {
        return ResponseEntity.ok(Result.success(branchServiceImpl.filterBranch(branchFilterDTO)));
    }

    @Override
    public ResponseEntity<Result<ListResult<BranchDTO>>> filterBranchBySpecification(@RequestBody final BranchFilterDTO search) {
        return ResponseEntity.ok(Result.success(branchServiceImpl.filterBranchBySpecification(search)));
    }
}

package org.example.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.branch.BranchDTO;
import org.example.dto.branch.BranchDetailsDTO;
import org.example.dto.branch.BranchIdNameCompanyDTO;
import org.example.dto.filter.BranchFilterDTO;
import org.example.service.BranchService;
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
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<Result<Long>> create(@RequestBody @Valid final BranchDTO branchDTO) {
        return ResponseEntity.ok(Result.success(branchService.create(branchDTO)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<BranchDetailsDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(branchService.get(id)));
    }

    @GetMapping
    public ResponseEntity<Result<List<CommonDTO>>> getList() {
        return ResponseEntity.ok(Result.success(branchService.getList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody @Valid final BranchDTO branchDTO) {
        return ResponseEntity.ok(Result.success(branchService.update(id, branchDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        branchService.delete(id);
        return ResponseEntity.ok(Result.success());
    }

    @GetMapping("/findAllBranches")
    public ResponseEntity<Result<List<BranchDTO>>> getAllBranches() {
        return ResponseEntity.ok(Result.success(branchService.getAllBranches()));
    }

    @GetMapping("/branchesByName/{name}")
    public ResponseEntity<Result<List<BranchDTO>>> getBranchesByName(@PathVariable("name") final String name) {
        return ResponseEntity.ok(Result.success(branchService.getBranchesByName(name)));
    }

    @GetMapping("/branchesByCompany/{id}")
    public ResponseEntity<Result<List<BranchDTO>>> getBranchesByCompany(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(branchService.getBranchesByCompanyId(id)));
    }

    @GetMapping("/getBranchCountByCompanyId/{id}")
    public ResponseEntity<Result<Long>> getBranchCountByCompanyId(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(branchService.getBranchCountByCompanyId(id)));
    }


    @GetMapping("/branchNames")
    public ResponseEntity<Result<List<String>>> getAllBranchNames() {
        return ResponseEntity.ok(Result.success(branchService.getAllBranchNames()));
    }

    @GetMapping("/findBranchList")
    public ResponseEntity<Result<List<BranchIdNameCompanyDTO>>> getBranchList() {
        return ResponseEntity.ok(Result.success(branchService.getBranchList()));
    }

    @GetMapping("/filterBranch")
    public ResponseEntity<Result<ListResult<BranchDTO>>> filterBranch(@RequestBody final BranchFilterDTO branchFilterDTO) {
        return ResponseEntity.ok(Result.success(branchService.filterBranch(branchFilterDTO)));
    }

    @GetMapping("/filterBranchBySpecification")
    public ResponseEntity<Result<ListResult<BranchDTO>>> filterBranchBySpecification(@RequestBody final BranchFilterDTO search) {
        return ResponseEntity.ok(Result.success(branchService.filterBranchBySpecification(search)));
    }
}

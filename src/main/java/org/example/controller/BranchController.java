package org.example.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.base.CommonDTO;
import org.example.dto.branch.BranchDTO;
import org.example.dto.branch.BranchDetailsDTO;
import org.example.dto.branch.BranchIdNameCompanyDTO;
import org.example.dto.filter.BranchFilterDTO;
import org.example.service.BranchService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BranchController {
    BranchService branchService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody BranchDTO branchDTO) {
        return ResponseEntity.ok(branchService.create(branchDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDetailsDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(branchService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<CommonDTO>> getList() {
        return ResponseEntity.ok(branchService.getList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable("id") Long id, @RequestBody BranchDTO branchDTO) {
        return ResponseEntity.ok(branchService.update(id, branchDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        branchService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAllBranches")
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @GetMapping("/branchesByName/{name}")
    public ResponseEntity<List<BranchDTO>> getBranchesByName(@PathVariable String name) {
        return ResponseEntity.ok(branchService.getBranchesByName(name));
    }


    @GetMapping("/branchesByCompany/{companyId}")
    public ResponseEntity<List<BranchDTO>> getBranchesByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(branchService.getBranchesByCompanyId(companyId));
    }

    @GetMapping("/getBranchCountByCompanyId/{companyId}")
    public ResponseEntity<Long> getBranchCountByCompanyId(@PathVariable("companyId") Long companyId) {
        return ResponseEntity.ok(branchService.getBranchCountByCompanyId(companyId));
    }


    @GetMapping("/branchNames")
    public ResponseEntity<List<String>> getAllBranchNames() {
        return ResponseEntity.ok(branchService.getAllBranchNames());
    }

    @GetMapping("/findBranchList")
    public ResponseEntity<List<BranchIdNameCompanyDTO>> getBranchList() {
        return ResponseEntity.ok(branchService.getBranchList());
    }

    @GetMapping("/filterBranch")
    public ResponseEntity<Page<BranchDTO>> filterBranch(@RequestBody BranchFilterDTO branchFilterDTO) {
        return ResponseEntity.ok(branchService.filterBranch(branchFilterDTO));
    }

    @GetMapping("/filterBranchBySpecification")
    public ResponseEntity<Page<BranchDTO>> filterBranchBySpecification(@RequestBody BranchFilterDTO search) {
        return ResponseEntity.ok(branchService.filterBranchBySpecification(search));
    }
}

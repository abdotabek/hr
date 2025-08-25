package org.example.controller.api;

import jakarta.validation.Valid;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.branch.BranchDTO;
import org.example.dto.branch.BranchDetailsDTO;
import org.example.dto.branch.BranchIdNameCompanyDTO;
import org.example.dto.filter.BranchFilterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/branches")
public interface BranchControllerApi {

    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody @Valid final BranchDTO branchDTO);

    @GetMapping("/{id}")
    ResponseEntity<Result<BranchDetailsDTO>> get(@PathVariable("id") final Long id);

    @GetMapping
    ResponseEntity<Result<List<CommonDTO>>> getList();

    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody @Valid final BranchDTO branchDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id);

    @GetMapping("/findAllBranches")
    ResponseEntity<Result<List<BranchDTO>>> getAllBranches();

    @GetMapping("/branchesByName/{name}")
    ResponseEntity<Result<List<BranchDTO>>> getBranchesByName(@PathVariable("name") final String name);

    @GetMapping("/branchesByCompany/{id}")
    ResponseEntity<Result<List<BranchDTO>>> getBranchesByCompany(@PathVariable("id") final Long id);

    @GetMapping("/getBranchCountByCompanyId/{id}")
    ResponseEntity<Result<Long>> getBranchCountByCompanyId(@PathVariable("id") final Long id);

    @GetMapping("/branchNames")
    ResponseEntity<Result<List<String>>> getAllBranchNames();

    @GetMapping("/findBranchList")
    ResponseEntity<Result<List<BranchIdNameCompanyDTO>>> getBranchList();

    @GetMapping("/filterBranch")
    ResponseEntity<Result<ListResult<BranchDTO>>> filterBranch(@RequestBody final BranchFilterDTO branchFilterDTO);

    @GetMapping("/filterBranchBySpecification")
    ResponseEntity<Result<ListResult<BranchDTO>>> filterBranchBySpecification(@RequestBody final BranchFilterDTO search);
}

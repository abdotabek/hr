package org.example.controller.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.config.doc.DocMethodAuth;
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

    @DocMethodAuth(
        summary = "Create branch",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody @Valid final BranchDTO branchDTO);

    @DocMethodAuth(
        summary = "Get branch by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = BranchDetailsDTO.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<Result<BranchDetailsDTO>> get(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Get branch list",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CommonDTO.class))
    )
    @GetMapping
    ResponseEntity<Result<List<CommonDTO>>> getList();

    @DocMethodAuth(
        summary = "Update branch by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody @Valid final BranchDTO branchDTO);

    @DocMethodAuth(
        summary = "Delete branch by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema())
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Find branch by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = BranchDTO.class))
    )
    @GetMapping("/findAllBranches")
    ResponseEntity<Result<List<BranchDTO>>> getAllBranches();

    @DocMethodAuth(
        summary = "Get branch by name",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = BranchDTO.class))
    )
    @GetMapping("/branchesByName/{name}")
    ResponseEntity<Result<List<BranchDTO>>> getBranchesByName(@PathVariable("name") final String name);

    @DocMethodAuth(
        summary = "Get branch by company",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = BranchDTO.class))
    )
    @GetMapping("/branchesByCompany/{id}")
    ResponseEntity<Result<List<BranchDTO>>> getBranchesByCompany(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Get branch count by companyId",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @GetMapping("/getBranchCountByCompanyId/{id}")
    ResponseEntity<Result<Long>> getBranchCountByCompanyId(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Get all branch name list",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = String.class))
    )
    @GetMapping("/branchNames")
    ResponseEntity<Result<List<String>>> getAllBranchNames();

    @DocMethodAuth(
        summary = "Get branchId company name",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = BranchIdNameCompanyDTO.class))
    )
    @GetMapping("/findBranchList")
    ResponseEntity<Result<List<BranchIdNameCompanyDTO>>> getBranchList();

    @DocMethodAuth(
        summary = "Filter branch",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = BranchDTO.class))
    )
    @GetMapping("/filterBranch")
    ResponseEntity<Result<ListResult<BranchDTO>>> filterBranch(@RequestBody final BranchFilterDTO branchFilterDTO);

    @DocMethodAuth(
        summary = "Filter by specification",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = BranchDTO.class))
    )
    @GetMapping("/filterBranchBySpecification")
    ResponseEntity<Result<ListResult<BranchDTO>>> filterBranchBySpecification(@RequestBody final BranchFilterDTO search);
}

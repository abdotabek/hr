package org.example.controller.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.config.doc.DocMethodAuth;
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

import java.util.List;

@RequestMapping("/api/departments")
public interface DepartmentControllerApi {
    @DocMethodAuth(
        summary = "Create department",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final CommonDTO departmentDTO);

    @DocMethodAuth(
        summary = "Get department by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = DepartmentDetailDTO.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<Result<DepartmentDetailDTO>> get(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Get department list",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CommonDTO.class))
    )
    @GetMapping
    ResponseEntity<Result<List<CommonDTO>>> getList();

    @DocMethodAuth(
        summary = "Update department by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO departmentDTO);

    @DocMethodAuth(
        summary = "Get department id name",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = DepartmentIdNameBranchIdDTO.class))
    )
    @GetMapping("/departments-id-name")
    ResponseEntity<Result<List<DepartmentIdNameBranchIdDTO>>> getDepartmentIdName();
}

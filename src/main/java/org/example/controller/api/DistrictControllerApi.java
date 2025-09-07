package org.example.controller.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.config.doc.DocMethodAuth;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.district.DistrictDetailDTO;
import org.example.dto.filter.DistrictFilterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/districts")
public interface DistrictControllerApi {

    @DocMethodAuth(
        summary = "Create district",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final CommonDTO districtDTO);

    @DocMethodAuth(
        summary = "Get district by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = DistrictDetailDTO.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<Result<DistrictDetailDTO>> get(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Get district list",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CommonDTO.class))
    )
    @GetMapping
    ResponseEntity<Result<List<CommonDTO>>> getList();

    @DocMethodAuth(
        summary = "Update district by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO districtDTO);

    @DocMethodAuth(
        summary = "Delete district by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema())
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Filter district",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CommonDTO.class))
    )
    @GetMapping("/filterDistrict")
    ResponseEntity<Result<ListResult<CommonDTO>>> filterDistrict(@RequestBody final DistrictFilterDTO search);

    @DocMethodAuth(
        summary = "Filter district by specification",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CommonDTO.class))
    )
    @GetMapping("/filterDistrictBySpecification")
    ResponseEntity<Result<ListResult<CommonDTO>>> filterDistrictBySpecification(@RequestBody final DistrictFilterDTO search);
}

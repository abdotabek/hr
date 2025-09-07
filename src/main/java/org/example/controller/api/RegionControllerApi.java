package org.example.controller.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.config.doc.DocMethodAuth;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.RegionFilterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/regions")
public interface RegionControllerApi {

    @DocMethodAuth(
        summary = "Create region",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final CommonDTO regionDTO);

    @DocMethodAuth(
        summary = "Get region by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CommonDTO.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<Result<CommonDTO>> get(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Get all region list",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CommonDTO.class))
    )
    @GetMapping
    ResponseEntity<Result<List<CommonDTO>>> getList();

    @DocMethodAuth(
        summary = "Update region by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO regionDTO);

    @DocMethodAuth(
        summary = "Delete region by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema())
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Filter region",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CommonDTO.class))
    )
    @GetMapping("/filterRegion")
    ResponseEntity<Result<ListResult<CommonDTO>>> filterRegion(@RequestBody final RegionFilterDTO search);

    @DocMethodAuth(
        summary = "Filter region by specification",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CommonDTO.class))
    )
    @GetMapping("/filterRegionBySpecification")
    ResponseEntity<Result<ListResult<CommonDTO>>> filterRegionBySpecification(@RequestBody final RegionFilterDTO search);
}

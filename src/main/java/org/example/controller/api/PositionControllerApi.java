package org.example.controller.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.config.doc.DocMethodAuth;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.PositionFilterDTO;
import org.example.dto.position.PositionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/positions")
public interface PositionControllerApi {

    @DocMethodAuth(
        summary = "Create position",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final PositionDTO positionDTO);

    @DocMethodAuth(
        summary = "Get positon by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CommonDTO.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<Result<CommonDTO>> get(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Get all positon list",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = PositionDTO.class))
    )
    @GetMapping
    ResponseEntity<Result<List<PositionDTO>>> getList();

    @DocMethodAuth(
        summary = "Update by position id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO positionDTO);

    @DocMethodAuth(
        summary = "Delete position by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema())
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Filter position",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CommonDTO.class))
    )
    @GetMapping("/filterPosition")
    ResponseEntity<Result<ListResult<CommonDTO>>> filterPosition(@RequestBody final PositionFilterDTO search);

    @DocMethodAuth(
        summary = "Filter position by specification",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CommonDTO.class))
    )
    @GetMapping("/filterPositionBySpecification")
    ResponseEntity<Result<ListResult<CommonDTO>>> filterPositionBySpecification(@RequestBody final PositionFilterDTO search);
}

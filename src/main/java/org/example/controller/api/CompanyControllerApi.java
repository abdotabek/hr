package org.example.controller.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.config.doc.DocMethodAuth;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.address.AddressDetailsDTO;
import org.example.dto.company.CompanyDTO;
import org.example.dto.company.CompanyDetailDTO;
import org.example.dto.company.CompanyListDTO;
import org.example.dto.filter.CompanyFilterDTO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/companies")
public interface CompanyControllerApi {

    @DocMethodAuth(
        summary = "Create company",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final CompanyDTO companyDTO);

    @DocMethodAuth(
        summary = "Get branch by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CompanyDTO.class))
    )
    @GetMapping("/getCompanyById/{id}")
    ResponseEntity<Result<?>> getBranchById(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Get company by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CompanyDetailDTO.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<Result<CompanyDetailDTO>> get(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Get company list",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CompanyListDTO.class))
    )
    @GetMapping
    ResponseEntity<Result<List<CompanyListDTO>>> getList();

    @DocMethodAuth(
        summary = "Update company by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CompanyDTO companyDTO);

    @DocMethodAuth(
        summary = "Update address",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PutMapping("/update-address/{id}")
    ResponseEntity<Result<Long>> updateAddress(@PathVariable("id") final Long id, @RequestBody final AddressDetailsDTO addressDTO);

    @DocMethodAuth(
        summary = "Delete company by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema())
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Get by tin",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CompanyDTO.class))
    )
    @GetMapping("/getByTin")
    ResponseEntity<Result<CompanyDTO>> getByTin(@RequestParam("tin") final String tin);

    @DocMethodAuth(
        summary = "Filter company",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CompanyDTO.class))
    )
    @GetMapping("/filterCompany")
    ResponseEntity<Result<ListResult<CompanyDTO>>> filterCompany(@ParameterObject final CompanyFilterDTO companyFilterDTO);

    @DocMethodAuth(
        summary = "Filter company for specification",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = CompanyDTO.class))
    )
    @GetMapping("/filterCompanyBySpecification")
    ResponseEntity<Result<ListResult<CompanyDTO>>> filterCompanyBySpecification(@ParameterObject final CompanyFilterDTO search);
}

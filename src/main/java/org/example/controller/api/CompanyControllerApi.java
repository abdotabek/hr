package org.example.controller.api;

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

    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final CompanyDTO companyDTO);

    @GetMapping("/getCompanyById/{id}")
    ResponseEntity<Result<?>> getBranchById(@PathVariable("id") final Long id);

    @GetMapping("/{id}")
    ResponseEntity<Result<CompanyDetailDTO>> get(@PathVariable("id") final Long id);

    @GetMapping
    ResponseEntity<Result<List<CompanyListDTO>>> getList();

    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CompanyDTO companyDTO);

    @PutMapping("/update-address/{id}")
    ResponseEntity<Result<Long>> updateAddress(@PathVariable("id") final Long id, @RequestBody final AddressDetailsDTO addressDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id);

    @GetMapping("/getByTin")
    ResponseEntity<Result<CompanyDTO>> getByTin(@RequestParam("tin") final String tin);

    @GetMapping("/filterCompany")
    ResponseEntity<Result<ListResult<CompanyDTO>>> filterCompany(@ParameterObject final CompanyFilterDTO companyFilterDTO);

    @GetMapping("/filterCompanyBySpecification")
    ResponseEntity<Result<ListResult<CompanyDTO>>> filterCompanyBySpecification(@ParameterObject final CompanyFilterDTO search);
}

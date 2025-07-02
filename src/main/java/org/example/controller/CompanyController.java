package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.address.AddressDetailsDTO;
import org.example.dto.company.CompanyDTO;
import org.example.dto.company.CompanyDetailDTO;
import org.example.dto.company.CompanyListDTO;
import org.example.dto.filter.CompanyFilterDTO;
import org.example.service.CompanyService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<Result<Long>> create(@RequestBody final CompanyDTO companyDTO) {
        return ResponseEntity.ok(Result.success(companyService.create(companyDTO)));
    }

    @GetMapping("/getCompanyById/{id}")
    public ResponseEntity<Result<?>> getBranchById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(companyService.getCompanyById(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<CompanyDetailDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(companyService.get(id)));
    }

    @GetMapping
    public ResponseEntity<Result<List<CompanyListDTO>>> getList() {
        return ResponseEntity.ok(Result.success(companyService.getList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CompanyDTO companyDTO) {
        return ResponseEntity.ok(Result.success(companyService.update(id, companyDTO)));
    }

    @PutMapping("/update-address/{id}")
    public ResponseEntity<Result<Long>> updateAddress(@PathVariable("id") final Long id, @RequestBody final AddressDetailsDTO addressDTO) {
        return ResponseEntity.ok(Result.success(companyService.updateAddress(id, addressDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        companyService.delete(id);
        return ResponseEntity.ok(Result.success());
    }

    @GetMapping("/getByTin")
    public ResponseEntity<Result<CompanyDTO>> getByTin(@RequestParam("tin") final String tin) {  //getByTin?tin=
        return ResponseEntity.ok(Result.success(companyService.getByTin(tin)));
    }

    @GetMapping("/filterCompany")
    public ResponseEntity<Result<ListResult<CompanyDTO>>> filterCompany(@ParameterObject final CompanyFilterDTO companyFilterDTO) {
        return ResponseEntity.ok(Result.success(companyService.filterCompany(companyFilterDTO)));
    }

    @GetMapping("/filterCompanyBySpecification")
    public ResponseEntity<Result<ListResult<CompanyDTO>>> filterCompanyBySpecification(@ParameterObject final CompanyFilterDTO search) {
        return ResponseEntity.ok(Result.success(companyService.filterCompanyBySpecification(search)));
    }
}

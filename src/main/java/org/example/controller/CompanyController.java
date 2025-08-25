package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.controller.api.CompanyControllerApi;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.address.AddressDetailsDTO;
import org.example.dto.company.CompanyDTO;
import org.example.dto.company.CompanyDetailDTO;
import org.example.dto.company.CompanyListDTO;
import org.example.dto.filter.CompanyFilterDTO;
import org.example.service.impl.CompanyServiceImpl;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompanyController implements CompanyControllerApi {

    private final CompanyServiceImpl companyServiceImpl;

    @Override
    public ResponseEntity<Result<Long>> create(@RequestBody final CompanyDTO companyDTO) {
        return ResponseEntity.ok(Result.success(companyServiceImpl.create(companyDTO)));
    }

    @Override
    public ResponseEntity<Result<?>> getBranchById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(companyServiceImpl.getCompanyById(id)));
    }

    @Override
    public ResponseEntity<Result<CompanyDetailDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(companyServiceImpl.get(id)));
    }

    @Override
    public ResponseEntity<Result<List<CompanyListDTO>>> getList() {
        return ResponseEntity.ok(Result.success(companyServiceImpl.getList()));
    }

    @Override
    public ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CompanyDTO companyDTO) {
        return ResponseEntity.ok(Result.success(companyServiceImpl.update(id, companyDTO)));
    }

    @Override
    public ResponseEntity<Result<Long>> updateAddress(@PathVariable("id") final Long id, @RequestBody final AddressDetailsDTO addressDTO) {
        return ResponseEntity.ok(Result.success(companyServiceImpl.updateAddress(id, addressDTO)));
    }

    @Override
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        companyServiceImpl.delete(id);
        return ResponseEntity.ok(Result.success());
    }

    @Override
    public ResponseEntity<Result<CompanyDTO>> getByTin(@RequestParam("tin") final String tin) {  //getByTin?tin=
        return ResponseEntity.ok(Result.success(companyServiceImpl.getByTin(tin)));
    }

    @Override
    public ResponseEntity<Result<ListResult<CompanyDTO>>> filterCompany(@ParameterObject final CompanyFilterDTO companyFilterDTO) {
        return ResponseEntity.ok(Result.success(companyServiceImpl.filterCompany(companyFilterDTO)));
    }

    @Override
    public ResponseEntity<Result<ListResult<CompanyDTO>>> filterCompanyBySpecification(@ParameterObject final CompanyFilterDTO search) {
        return ResponseEntity.ok(Result.success(companyServiceImpl.filterCompanyBySpecification(search)));
    }
}

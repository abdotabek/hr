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
import org.example.service.CompanyService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController implements CompanyControllerApi {

    private final CompanyService companyService;

    @Override
    public ResponseEntity<Result<Long>> create(@RequestBody final CompanyDTO companyDTO) {
        return ResponseEntity.ok(Result.success(companyService.create(companyDTO)));
    }

    @Override
    public ResponseEntity<Result<?>> getBranchById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(companyService.getCompanyById(id)));
    }

    @Override
    public ResponseEntity<Result<CompanyDetailDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(companyService.get(id)));
    }

    @Override
    public ResponseEntity<Result<List<CompanyListDTO>>> getList() {
        return ResponseEntity.ok(Result.success(companyService.getList()));
    }

    @Override
    public ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CompanyDTO companyDTO) {
        return ResponseEntity.ok(Result.success(companyService.update(id, companyDTO)));
    }

    @Override
    public ResponseEntity<Result<Long>> updateAddress(@PathVariable("id") final Long id, @RequestBody final AddressDetailsDTO addressDTO) {
        return ResponseEntity.ok(Result.success(companyService.updateAddress(id, addressDTO)));
    }

    @Override
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        companyService.delete(id);
        return ResponseEntity.ok(Result.success());
    }

    @Override
    public ResponseEntity<Result<CompanyDTO>> getByTin(@RequestParam("tin") final String tin) {  //getByTin?tin=
        return ResponseEntity.ok(Result.success(companyService.getByTin(tin)));
    }

    @Override
    public ResponseEntity<Result<ListResult<CompanyDTO>>> filterCompany(@ParameterObject final CompanyFilterDTO companyFilterDTO) {
        return ResponseEntity.ok(Result.success(companyService.filterCompany(companyFilterDTO)));
    }

    @Override
    public ResponseEntity<Result<ListResult<CompanyDTO>>> filterCompanyBySpecification(@ParameterObject final CompanyFilterDTO search) {
        return ResponseEntity.ok(Result.success(companyService.filterCompanyBySpecification(search)));
    }
}

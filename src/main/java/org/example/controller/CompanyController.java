package org.example.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.company.CompanyDTO;
import org.example.dto.company.CompanyDetailDTO;
import org.example.dto.company.CompanyListDTO;
import org.example.dto.filter.CompanyFilterDTO;
import org.example.service.CompanyService;
import org.example.service.RabbitMQService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CompanyController {

    CompanyService companyService;
    RabbitMQService rabbitMQService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CompanyDTO companyDTO) {
        return ResponseEntity.ok(companyService.create(companyDTO));
    }

    @GetMapping("/getCompanyById/{id}")
    public ResponseEntity<?> getBranchById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDetailDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(companyService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<CompanyListDTO>> getList() {
        return ResponseEntity.ok(companyService.getList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable("id") Long id, @RequestBody CompanyDTO companyDTO) {
        return ResponseEntity.ok(companyService.update(id, companyDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        companyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getByTin")
    public ResponseEntity<CompanyDTO> getByTin(@RequestParam("tin") String tin) {  //getByTin?tin=
        return ResponseEntity.ok(companyService.getByTin(tin));
    }

    @GetMapping("/filterCompany")
    public ResponseEntity<Page<CompanyDTO>> filterCompany(@RequestBody CompanyFilterDTO companyFilterDTO) {
        return ResponseEntity.ok(companyService.filterCompany(companyFilterDTO));
    }

    @GetMapping("/filterCompanyBySpecification")
    public ResponseEntity<Page<CompanyDTO>> filterCompanyBySpecification(@RequestBody CompanyFilterDTO search) {
        return ResponseEntity.ok(companyService.filterCompanyBySpecification(search));
    }
    /*@DeleteMapping("/batch")
    public ResponseEntity<Void> deleteCompanyBatch(@RequestBody List<Long> ids) {
        rabbitMQService.deleteCompany(ids);
        return ResponseEntity.ok().build();
    }*/
}

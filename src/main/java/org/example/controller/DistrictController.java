package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.controller.api.DistrictControllerApi;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.district.DistrictDetailDTO;
import org.example.dto.filter.DistrictFilterDTO;
import org.example.service.DistrictService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
@RequiredArgsConstructor
public class DistrictController implements DistrictControllerApi {

    private final DistrictService districtService;

    @Override
    public ResponseEntity<Result<Long>> create(@RequestBody final CommonDTO districtDTO) {
        return ResponseEntity.ok(Result.success(districtService.create(districtDTO)));
    }

    @Override
    public ResponseEntity<Result<DistrictDetailDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(districtService.get(id)));
    }

    @Override
    public ResponseEntity<Result<List<CommonDTO>>> getList() {
        return ResponseEntity.ok(Result.success(districtService.getList()));
    }

    @Override
    public ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO districtDTO) {
        return ResponseEntity.ok(Result.success(districtService.update(id, districtDTO)));
    }

    @Override
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        districtService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterDistrict(@RequestBody final DistrictFilterDTO search) {
        return ResponseEntity.ok(Result.success(districtService.filterDistrict(search)));
    }

    @Override
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterDistrictBySpecification(@RequestBody final DistrictFilterDTO search) {
        return ResponseEntity.ok(Result.success(districtService.filterDistrictBySpecification(search)));
    }
}

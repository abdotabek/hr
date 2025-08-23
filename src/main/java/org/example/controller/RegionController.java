package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.controller.api.RegionControllerApi;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.RegionFilterDTO;
import org.example.service.RegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController implements RegionControllerApi {

    private final RegionService regionService;

    @Override
    public ResponseEntity<Result<Long>> create(@RequestBody final CommonDTO regionDTO) {
        return ResponseEntity.ok(Result.success(regionService.create(regionDTO)));
    }

    @Override
    public ResponseEntity<Result<CommonDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(regionService.get(id)));
    }

    @Override
    public ResponseEntity<Result<List<CommonDTO>>> getList() {
        return ResponseEntity.ok(Result.success(regionService.getList()));
    }

    @Override
    public ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO regionDTO) {
        return ResponseEntity.ok(Result.success(regionService.update(id, regionDTO)));
    }

    @Override
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        regionService.delete(id);
        return ResponseEntity.ok(Result.success());
    }

    @Override
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterRegion(@RequestBody final RegionFilterDTO search) {
        return ResponseEntity.ok(Result.success(regionService.filterRegion(search)));
    }

    @Override
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterRegionBySpecification(@RequestBody final RegionFilterDTO search) {
        return ResponseEntity.ok(Result.success(regionService.filterRegionBySpecification(search)));
    }
}

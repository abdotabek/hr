package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.controller.api.PositionControllerApi;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.PositionFilterDTO;
import org.example.dto.position.PositionDTO;
import org.example.service.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PositionController implements PositionControllerApi {

    private final PositionService positionService;

    @Override
    public ResponseEntity<Result<Long>> create(@RequestBody final PositionDTO positionDTO) {
        return ResponseEntity.ok(Result.success(positionService.create(positionDTO)));
    }

    @Override
    public ResponseEntity<Result<CommonDTO>> get(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(Result.success(positionService.get(id)));
    }

    @Override
    public ResponseEntity<Result<List<PositionDTO>>> getList() {
        return ResponseEntity.ok(Result.success(positionService.getList()));
    }

    @Override
    public ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final CommonDTO positionDTO) {
        return ResponseEntity.ok(Result.success(positionService.update(id, positionDTO)));
    }

    @Override
    public ResponseEntity<Result<Void>> delete(@PathVariable("id") final Long id) {
        positionService.delete(id);
        return ResponseEntity.ok(Result.success());
    }

    @Override
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterPosition(@RequestBody final PositionFilterDTO search) {
        return ResponseEntity.ok(Result.success(positionService.filterPosition(search)));
    }

    @Override
    public ResponseEntity<Result<ListResult<CommonDTO>>> filterPositionBySpecification(@RequestBody final PositionFilterDTO search) {
        return ResponseEntity.ok(Result.success(positionService.filterPositionBySpecification(search)));
    }
}

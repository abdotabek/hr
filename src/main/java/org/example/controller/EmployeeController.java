package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.controller.api.EmployeeControllerApi;
import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.employee.EmployeeDetailDTO;
import org.example.dto.employee.EmployeeListDTO;
import org.example.dto.enums.GeneralStatus;
import org.example.dto.filter.EmployeeFilterDTO;
import org.example.enums.AppLanguage;
import org.example.service.EmployeeService;
import org.example.service.RabbitMQService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeControllerApi {

    private final EmployeeService employeeService;
    private final RabbitMQService rabbitMQService;

    @Override
    public ResponseEntity<Result<Long>> create(@RequestBody final EmployeeDTO employeeDTO,
                                               @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(Result.success(employeeService.create(employeeDTO, language)));
    }

    @Override
    public ResponseEntity<Result<EmployeeDetailDTO>> get(@PathVariable("id") final Long id,
                                                         @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(Result.success(employeeService.get(id, language)));
    }

    @Override
    public ResponseEntity<Result<List<EmployeeListDTO>>> getList() {
        return ResponseEntity.ok(Result.success(employeeService.getList()));
    }

    @Override
    public ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(Result.success(employeeService.update(id, employeeDTO)));
    }

    @Override
    public ResponseEntity<Result<Void>> updateStatus(@PathVariable("id") final Long id, @RequestBody final GeneralStatus status) {
        employeeService.updateStatus(id, status);
        return ResponseEntity.ok(Result.success());
    }

    @Override
    public ResponseEntity<Result<Void>> deleteEmployeeBatch(@RequestBody final List<Long> ids) {
        rabbitMQService.deleteEmployee(ids);
        return ResponseEntity.ok(Result.success());
    }

    @Override
    public ResponseEntity<Result<List<EmployeeDTO>>> getAllEmployee() {
        return ResponseEntity.ok(Result.success(employeeService.getAllEmployees()));
    }

    @Override
    public ResponseEntity<Result<ListResult<EmployeeDTO>>> filterNameAndSurname(@RequestBody final EmployeeFilterDTO filter) {
        return ResponseEntity.ok(Result.success(employeeService.filterNameAndSurname(filter)));
    }

    @Override
    public ResponseEntity<Result<ListResult<EmployeeDTO>>> filterByDepartmentName(@RequestBody final EmployeeFilterDTO filter) {
        return ResponseEntity.ok(Result.success(employeeService.filterByDepartmentName(filter)));
    }

    @Override
    public ResponseEntity<Result<ListResult<EmployeeDTO>>> filterByNameSurname(@RequestBody final EmployeeFilterDTO filter) {
        return ResponseEntity.ok(Result.success(employeeService.filterByNameSurname(filter)));
    }

    @Override
    public ResponseEntity<Result<ListResult<EmployeeDTO>>> filterEmployeeByPosition(@RequestBody final EmployeeFilterDTO filter) {
        return ResponseEntity.ok(Result.success(employeeService.filterEmployeeByPosition(filter)));
    }

    @Override
    public ResponseEntity<Result<List<EmployeeDTO>>> getByName(@PathVariable("name") final String name) {
        return ResponseEntity.ok(Result.success(employeeService.getByName(name)));
    }

    @Override
    public ResponseEntity<Result<List<?>>> getIdNameBySurname(@PathVariable("surname") final String surname) {
        return ResponseEntity.ok(Result.success(employeeService.findIdAndNameBySurname(surname)));
    }

    @Override
    public ResponseEntity<Result<List<?>>> getEmployeeByPhone(@PathVariable("phone") final String phone) {
        return ResponseEntity.ok(Result.success(employeeService.getEmployeeDTOByPhone(phone)));
    }

    @Override
    public ResponseEntity<Result<Long>> getEmployeeByDepartment(@RequestParam final String departmentName) {
        return ResponseEntity.ok(Result.success(employeeService.countEmployeeByDepartment(departmentName)));
    }

    @Override
    public ResponseEntity<Result<ListResult<EmployeeDTO>>> filterEmployee(@RequestBody final EmployeeFilterDTO search) {
        return ResponseEntity.ok(Result.success(employeeService.filterEmployee(search)));
    }

    @Override
    public ResponseEntity<Result<ListResult<EmployeeDTO>>> filterBySpecification(@RequestBody final EmployeeFilterDTO search) {
        return ResponseEntity.ok(Result.success(employeeService.filterBySpecification(search)));
    }

    @Override
    public ResponseEntity<Result<Void>> dismissedEmployee(@PathVariable("id") final Long id) {
        employeeService.dismissedEmployee(id);
        return ResponseEntity.ok(Result.success());
    }

    @Override
    public ResponseEntity<Result<Void>> saveBlockList(@PathVariable("id") final Long id) {
        employeeService.saveBlockList(id);
        return ResponseEntity.ok(Result.success());
    }
}

package org.example.controller.api;

import org.example.dto.ListResult;
import org.example.dto.Result;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.employee.EmployeeDetailDTO;
import org.example.dto.employee.EmployeeListDTO;
import org.example.dto.enums.GeneralStatus;
import org.example.dto.filter.EmployeeFilterDTO;
import org.example.enums.AppLanguage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/employees")
public interface EmployeeControllerApi {

    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final EmployeeDTO employeeDTO,
                                        @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language);

    @GetMapping("/{id}")
    ResponseEntity<Result<EmployeeDetailDTO>> get(@PathVariable("id") final Long id,
                                                  @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language);

    @GetMapping
    ResponseEntity<Result<List<EmployeeListDTO>>> getList();

    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final EmployeeDTO employeeDTO);

    @PutMapping("/{id}/status")
    ResponseEntity<Result<Void>> updateStatus(@PathVariable("id") final Long id, @RequestBody final GeneralStatus status);

    @DeleteMapping("/batch")
    ResponseEntity<Result<Void>> deleteEmployeeBatch(@RequestBody final List<Long> ids);

    @GetMapping("/getAllEmployees")
    ResponseEntity<Result<List<EmployeeDTO>>> getAllEmployee();

    @GetMapping("/filterNameAndSurname")
    ResponseEntity<Result<ListResult<EmployeeDTO>>> filterNameAndSurname(@RequestBody final EmployeeFilterDTO filter);

    @GetMapping("/filterByDepartmentName")
    ResponseEntity<Result<ListResult<EmployeeDTO>>> filterByDepartmentName(@RequestBody final EmployeeFilterDTO filter);

    @GetMapping("/filterByNameSurname")
    ResponseEntity<Result<ListResult<EmployeeDTO>>> filterByNameSurname(@RequestBody final EmployeeFilterDTO filter);

    @GetMapping("/filterEmployeeByPosition")
    ResponseEntity<Result<ListResult<EmployeeDTO>>> filterEmployeeByPosition(@RequestBody final EmployeeFilterDTO filter);

    @GetMapping("/getByName/{name}")
    ResponseEntity<Result<List<EmployeeDTO>>> getByName(@PathVariable("name") final String name);

    @GetMapping("/findIdAndNameBySurname/{surname}")
    ResponseEntity<Result<List<?>>> getIdNameBySurname(@PathVariable("surname") final String surname);

    @GetMapping("/findEmployeeByPhone/{phone}")
    ResponseEntity<Result<List<?>>> getEmployeeByPhone(@PathVariable("phone") final String phone);

    @GetMapping("/countEmployeeByDepartment")
    ResponseEntity<Result<Long>> getEmployeeByDepartment(@RequestParam final String departmentName);

    @GetMapping("/filterEmployee")
    ResponseEntity<Result<ListResult<EmployeeDTO>>> filterEmployee(@RequestBody final EmployeeFilterDTO search);

    @GetMapping("/filterBySpecification")
    ResponseEntity<Result<ListResult<EmployeeDTO>>> filterBySpecification(@RequestBody final EmployeeFilterDTO search);

    @PatchMapping("/dismissedEmployee/{id}")
    ResponseEntity<Result<Void>> dismissedEmployee(@PathVariable("id") final Long id);

    @PatchMapping("/saveBlockList/{id}")
    ResponseEntity<Result<Void>> saveBlockList(@PathVariable("id") final Long id);
}

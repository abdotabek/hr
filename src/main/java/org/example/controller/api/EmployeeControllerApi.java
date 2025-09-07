package org.example.controller.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.config.doc.DocMethodAuth;
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

    @DocMethodAuth(
        summary = "Create employee",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PostMapping
    ResponseEntity<Result<Long>> create(@RequestBody final EmployeeDTO employeeDTO,
                                        @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language);

    @DocMethodAuth(
        summary = "Get employee by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = EmployeeDetailDTO.class))
    )
    @GetMapping("/{id}")
    ResponseEntity<Result<EmployeeDetailDTO>> get(@PathVariable("id") final Long id,
                                                  @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language);

    @DocMethodAuth(
        summary = "Get employee list",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = EmployeeListDTO.class))
    )
    @GetMapping
    ResponseEntity<Result<List<EmployeeListDTO>>> getList();

    @DocMethodAuth(
        summary = "Update employee by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PutMapping("/{id}")
    ResponseEntity<Result<Long>> update(@PathVariable("id") final Long id, @RequestBody final EmployeeDTO employeeDTO);

    @DocMethodAuth(
        summary = "Update employee status by id",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema())
    )
    @PutMapping("/{id}/status")
    ResponseEntity<Result<Void>> updateStatus(@PathVariable("id") final Long id, @RequestBody final GeneralStatus status);

    @DocMethodAuth(
        summary = "Delete employee batch",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema())
    )
    @DeleteMapping("/batch")
    ResponseEntity<Result<Void>> deleteEmployeeBatch(@RequestBody final List<Long> ids);

    @DocMethodAuth(
        summary = "Get all employee list",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = EmployeeDTO.class))
    )
    @GetMapping("/getAllEmployees")
    ResponseEntity<Result<List<EmployeeDTO>>> getAllEmployee();

    @DocMethodAuth(
        summary = "Filter employee name or surname",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = EmployeeDTO.class))
    )
    @GetMapping("/filterNameAndSurname")
    ResponseEntity<Result<ListResult<EmployeeDTO>>> filterNameAndSurname(@RequestBody final EmployeeFilterDTO filter);

    @DocMethodAuth(
        summary = "Filter employee by department",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = EmployeeDTO.class))
    )
    @GetMapping("/filterByDepartmentName")
    ResponseEntity<Result<ListResult<EmployeeDTO>>> filterByDepartmentName(@RequestBody final EmployeeFilterDTO filter);

    @DocMethodAuth(
        summary = "Filter employee by nameSurname",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = EmployeeDTO.class))
    )
    @GetMapping("/filterByNameSurname")
    ResponseEntity<Result<ListResult<EmployeeDTO>>> filterByNameSurname(@RequestBody final EmployeeFilterDTO filter);

    @DocMethodAuth(
        summary = "Filter employee by position",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = EmployeeDTO.class))
    )
    @GetMapping("/filterEmployeeByPosition")
    ResponseEntity<Result<ListResult<EmployeeDTO>>> filterEmployeeByPosition(@RequestBody final EmployeeFilterDTO filter);

    @DocMethodAuth(
        summary = "Get employee by name",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = EmployeeDTO.class))
    )
    @GetMapping("/getByName/{name}")
    ResponseEntity<Result<List<EmployeeDTO>>> getByName(@PathVariable("name") final String name);

    @DocMethodAuth(
        summary = "Find id and by surname",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Object.class))
    )
    @GetMapping("/findIdAndNameBySurname/{surname}")
    ResponseEntity<Result<List<?>>> getIdNameBySurname(@PathVariable("surname") final String surname);

    @DocMethodAuth(
        summary = "Get employee by phone",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Object.class))
    )
    @GetMapping("/findEmployeeByPhone/{phone}")
    ResponseEntity<Result<List<?>>> getEmployeeByPhone(@PathVariable("phone") final String phone);

    @DocMethodAuth(
        summary = "Get employee by department",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = Long.class))
    )
    @GetMapping("/countEmployeeByDepartment")
    ResponseEntity<Result<Long>> getEmployeeByDepartment(@RequestParam final String departmentName);

    @DocMethodAuth(
        summary = "Filter employee",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = EmployeeDTO.class))
    )
    @GetMapping("/filterEmployee")
    ResponseEntity<Result<ListResult<EmployeeDTO>>> filterEmployee(@RequestBody final EmployeeFilterDTO search);

    @DocMethodAuth(
        summary = "Filter employee by specification",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = EmployeeDTO.class))
    )
    @GetMapping("/filterBySpecification")
    ResponseEntity<Result<ListResult<EmployeeDTO>>> filterBySpecification(@RequestBody final EmployeeFilterDTO search);

    @DocMethodAuth(
        summary = "Dismissed employee",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema())
    )
    @PatchMapping("/dismissedEmployee/{id}")
    ResponseEntity<Result<Void>> dismissedEmployee(@PathVariable("id") final Long id);

    @DocMethodAuth(
        summary = "Save block list",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema())
    )
    @PatchMapping("/saveBlockList/{id}")
    ResponseEntity<Result<Void>> saveBlockList(@PathVariable("id") final Long id);
}

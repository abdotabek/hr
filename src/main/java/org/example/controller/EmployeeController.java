package org.example.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.employee.EmployeeDetailDTO;
import org.example.dto.employee.EmployeeListDTO;
import org.example.dto.filter.EmployeeFilterDTO;
import org.example.dto.jwt.AuthRequestDTO;
import org.example.dto.jwt.AuthResponseDTO;
import org.example.dto.jwt.TokenDTO;
import org.example.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmployeeController {
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.create(employeeDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDetailDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeListDTO>> getList() {
        return ResponseEntity.ok(employeeService.getList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.update(id, employeeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/filterNameAndSurname")
    public ResponseEntity<Page<EmployeeDTO>> filterNameAndSurname(@RequestBody EmployeeFilterDTO filter) {
        return ResponseEntity.ok(employeeService.filterNameAndSurname(filter));
    }

    @GetMapping("/filterByDepartmentName")
    public ResponseEntity<Page<EmployeeDTO>> filterByDepartmentName(@RequestBody EmployeeFilterDTO filter) {
        return ResponseEntity.ok(employeeService.filterByDepartmentName(filter));
    }

    @GetMapping("/filterByNameSurname")
    public ResponseEntity<Page<EmployeeDTO>> filterByNameSurname(@RequestBody EmployeeFilterDTO filter) {
        return ResponseEntity.ok(employeeService.filterByNameSurname(filter));
    }

    @GetMapping("/filterEmployeeByPosition")
    public ResponseEntity<Page<EmployeeDTO>> filterEmployeeByPosition(@RequestBody EmployeeFilterDTO filter) {
        return ResponseEntity.ok(employeeService.filterEmployeeByPosition(filter));
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<List<EmployeeDTO>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(employeeService.getByName(name));
    }

    @GetMapping("/findIdAndNameBySurname/{surname}")
    public ResponseEntity<List<?>> getIdNameBySurname(@PathVariable String surname) {
        return ResponseEntity.ok(employeeService.findIdAndNameBySurname(surname));
    }

    @GetMapping("/findEmployeeByPhone/{phone}")
    public ResponseEntity<List<?>> getEmployeeByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(employeeService.getEmployeeDTOByPhone(phone));
    }

    @GetMapping("/countEmployeeByDepartment")
    public ResponseEntity<Long> getEmployeeByDepartment(@RequestParam String departmentName) {
        return ResponseEntity.ok(employeeService.countEmployeeByDepartment(departmentName));
    }

    @GetMapping("/filterEmployee")
    public ResponseEntity<Page<EmployeeDTO>> filterEmployee(@RequestBody EmployeeFilterDTO search) {
        return ResponseEntity.ok(employeeService.filterEmployee(search));
    }

    @GetMapping("/filterBySpecification")
    public ResponseEntity<Page<EmployeeDTO>> filterBySpecification(@RequestBody EmployeeFilterDTO search) {
        return ResponseEntity.ok(employeeService.filterBySpecification(search));
    }

    @PostMapping("/registration")
    public ResponseEntity<EmployeeDTO> creteRegistration(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.registration(employeeDTO));
    }

    /*@PostMapping("/authorization")
    public ResponseEntity<AuthResponseDTO> authorization(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(employeeService.authorization(authRequestDTO));
    }*/

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenDTO tokenDTO) {
        return ResponseEntity.ok(employeeService.getNewAccessToken(tokenDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> dismissedEmployee(@PathVariable("id") Long id) {
        employeeService.dismissedEmployee(id);
        return ResponseEntity.ok().build();
    }
}

package org.example.repository;

import org.example.dto.employee.EmployeeDTO;
import org.example.dto.filter.EmployeeFilterDTO;
import org.springframework.data.domain.Page;

public interface EmployeeRepositoryCustom {
    Page<EmployeeDTO> filterNameAndSurname(EmployeeFilterDTO search);

    Page<EmployeeDTO> filterByDepartmentName(EmployeeFilterDTO search);

    Page<EmployeeDTO> filterByNameSurname(EmployeeFilterDTO search);

    Page<EmployeeDTO> filterEmployeeByPosition(EmployeeFilterDTO search);

    Page<EmployeeDTO> filterEmployee(EmployeeFilterDTO search);

    Page<EmployeeDTO> filterBySpecification(EmployeeFilterDTO search);
}

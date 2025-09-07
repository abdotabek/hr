package org.example.service;

import org.example.dto.ListResult;
import org.example.dto.LoginVM;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.employee.EmployeeDetailDTO;
import org.example.dto.employee.EmployeeListDTO;
import org.example.dto.enums.GeneralStatus;
import org.example.dto.filter.EmployeeFilterDTO;
import org.example.enums.AppLanguage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeService {

    @Transactional
    Long create(final EmployeeDTO employeeDTO, AppLanguage language);

    EmployeeDetailDTO get(final Long id, AppLanguage language);

    List<EmployeeListDTO> getList();

    @Transactional
    Long update(final Long id, final EmployeeDTO employeeDTO);

    void authenticationSuccessHandler(final LoginVM vm);

    void authenticationFailureHandler(LoginVM vm, Exception e);

    @Transactional
    void delete(final Long id);

    ListResult<EmployeeDTO> filterNameAndSurname(final EmployeeFilterDTO filter);

    ListResult<EmployeeDTO> filterByDepartmentName(final EmployeeFilterDTO filter);

    ListResult<EmployeeDTO> filterByNameSurname(final EmployeeFilterDTO filter);

    ListResult<EmployeeDTO> filterEmployeeByPosition(final EmployeeFilterDTO filter);

    List<EmployeeDTO> getAllEmployees();

    List<EmployeeDTO> getByName(final String name);

    List<?> findIdAndNameBySurname(final String surname);

    List<?> getEmployeeDTOByPhone(final String phone);

    Long countEmployeeByDepartment(final String departmentName);

    ListResult<EmployeeDTO> filterEmployee(final EmployeeFilterDTO search);

    ListResult<EmployeeDTO> filterBySpecification(final EmployeeFilterDTO search);

    @Transactional
    void dismissedEmployee(final Long id);

    @Transactional
    void saveBlockList(final Long id);

    @Transactional
    void updateStatus(final Long id, final GeneralStatus status);
}

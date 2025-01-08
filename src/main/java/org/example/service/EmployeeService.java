package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.employee.EmployeeDetailDTO;
import org.example.dto.employee.EmployeeListDTO;
import org.example.dto.enums.GeneralStatus;
import org.example.dto.filter.EmployeeFilterDTO;
import org.example.entity.Employee;
import org.example.entity.redis.BlockList;
import org.example.entity.redis.TokenStore;
import org.example.exception.ExceptionUtil;
import org.example.exception.NotFoundException;
import org.example.repository.BlockListRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.TokenStoreRepository;
import org.example.repository.mapper.EmployeeMapper;
import org.example.service.custom.EmployeeCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmployeeService {

    EmployeeRepository employeeRepository;
    EmployeeMapper mapper;
    EmployeeCustomRepository customRepository;
    EntityManager entityManager;
    TokenStoreRepository tokenStoreRepository;
    BlockListRepository blockListRepository;


    @Transactional
    public Long create(EmployeeDTO employeeDTO) {
        if (employeeRepository.existsEmployeeByEmail(employeeDTO.getEmail())) {
            throw ExceptionUtil.throwConflictException("employee with this email already exists!");
        }
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setEmail(employeeDTO.getEmail());
        employee.setCompanyId(employeeDTO.getCompanyId());
        employee.setBranchId(employeeDTO.getBranchId());
        employee.setDepartmentId(employeeDTO.getDepartmentId());
        employee.setPositionId(employeeDTO.getPositionId());

        return employeeRepository.save(employee).getId();
    }

    public EmployeeDetailDTO get(Long id) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    EmployeeDetailDTO employeeDetailDTO = new EmployeeDetailDTO();
                    employeeDetailDTO.setId(employee.getId());
                    employeeDetailDTO.setFirstName(employee.getFirstName());
                    employeeDetailDTO.setLastName(employee.getLastName());
                    employeeDetailDTO.setPhoneNumber(employee.getPhoneNumber());
                    employeeDetailDTO.setCompanyName(employee.getCompany() != null ? employee.getCompany().getName() : null);
                    employeeDetailDTO.setBranchName(employee.getBranch() != null ? employee.getBranch().getName() : null);
                    employeeDetailDTO.setPositionName(employee.getPosition() != null ? employee.getPosition().getName() : null);
                    employeeDetailDTO.setDepartmentName(employee.getDepartment() != null ? employee.getDepartment().getName() : null);
                    return employeeDetailDTO;
                }).orElseThrow(() ->
                        ExceptionUtil.throwNotFoundException("employee with this ID does not exist"));
    }

    public List<EmployeeListDTO> getList() {
        return employeeRepository.findAll()
                .stream().map(employee -> {
                    EmployeeListDTO employeeListDTO = new EmployeeListDTO();
                    employeeListDTO.setId(employee.getId());
                    employeeListDTO.setFirstName(employee.getFirstName());
                    employeeListDTO.setLastName(employee.getLastName());
                    employeeListDTO.setPositionName(employee.getPosition() != null ? employee.getPosition().getName() : null);
                    return employeeListDTO;
                }).collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id, EmployeeDTO employeeDTO) {
        employeeRepository.findById(id)
                .map(employee -> {
                    employee.setFirstName(employeeDTO.getFirstName());
                    employee.setLastName(employeeDTO.getLastName());
                    employee.setPhoneNumber(employeeDTO.getPhoneNumber());
                    employee.setCompanyId(employeeDTO.getCompanyId());
                    employee.setBranchId(employeeDTO.getBranchId());
                    employee.setDepartmentId(employeeDTO.getDepartmentId());
                    employee.setPositionId(employeeDTO.getPositionId());

                    employeeRepository.save(employee);
                    return employee.getId();
                }).orElseThrow(() -> new NotFoundException("employee with this id does not exist!"));
        return employeeDTO.getId();
    }

    @Transactional
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }


    public Page<EmployeeDTO> filterNameAndSurname(EmployeeFilterDTO filter) {
        return customRepository.filterNameAndSurname(filter);
    }

    public Page<EmployeeDTO> filterByDepartmentName(EmployeeFilterDTO filter) {
        return customRepository.filterByDepartmentName(filter);
    }

    public Page<EmployeeDTO> filterByNameSurname(EmployeeFilterDTO filter) {
        return customRepository.filterByNameSurname(filter);
    }

    public Page<EmployeeDTO> filterEmployeeByPosition(EmployeeFilterDTO filter) {
        return customRepository.filterEmployeeByPosition(filter);
    }


    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAllEmployees().stream().map(mapper::toDTO).toList();
    }

    public List<EmployeeDTO> getByName(String name) {
        return employeeRepository.findByName(name).stream().map(mapper::toDTO).toList();
    }

    public List<?> findIdAndNameBySurname(String surname) {
        return employeeRepository.findIdsAndNamesBySurname(surname);
    }

    public List<?> getEmployeeDTOByPhone(String phone) {
        return employeeRepository.findEmployeeDTOByPhone(phone);
    }

    public Long countEmployeeByDepartment(String departmentName) {
        TypedQuery<Long> query = entityManager.createQuery(
                "select count (e) from Employee e inner join Department d on e.id = d.id where d.name =:name", Long.class);
        query.setParameter("name", departmentName);
        return query.getSingleResult();
    }

    public Page<EmployeeDTO> filterEmployee(EmployeeFilterDTO search) {
        return customRepository.filterEmployee(search);
    }

    public Page<EmployeeDTO> filterBySpecification(EmployeeFilterDTO search) {
        return customRepository.filterBySpecification(search);
    }

    @Transactional
    public void dismissedEmployee(Long id) {
        employeeRepository.findById(id)
                .map(employee -> {
                    employee.setStatus(GeneralStatus.BLOCK);
                    return employeeRepository.save(employee);
                }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("employee with this id does not exist!"));

        for (TokenStore tokenStore : tokenStoreRepository.findAll()) {
            if (tokenStore != null && tokenStore.getEmployeeId().equals(id)) {
                tokenStoreRepository.delete(tokenStore);
            }
        }
    }

    @Transactional
    public void saveBlockList(Long id) {
        employeeRepository.findById(id)
                .map(employee -> {
                    employee.setStatus(GeneralStatus.BLOCK);
                    return employeeRepository.save(employee);
                }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("employee with this id does not exist!"));

        if (!blockListRepository.existsById(id)) {
            // Сохраняем в Redis
            blockListRepository.save(new BlockList(id));
        }
    }

}

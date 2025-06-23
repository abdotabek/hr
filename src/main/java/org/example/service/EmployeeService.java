package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.dto.ListResult;
import org.example.dto.LoginVM;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.employee.EmployeeDetailDTO;
import org.example.dto.employee.EmployeeListDTO;
import org.example.dto.enums.GeneralStatus;
import org.example.dto.filter.EmployeeFilterDTO;
import org.example.entity.Employee;
import org.example.entity.redis.BlackList;
import org.example.exception.ExceptionUtil;
import org.example.exception.NotFoundException;
import org.example.repository.BlockListRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.TokenStoreRepository;
import org.example.repository.mapper.EmployeeMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper mapper;
    private final EntityManager entityManager;
    private final TokenStoreRepository tokenStoreRepository;
    private final BlockListRepository blockListRepository;


    @Transactional
    public Long create(final EmployeeDTO employeeDTO) {
        if (employeeRepository.existsEmployeeByEmail(employeeDTO.getEmail())) {
            throw ExceptionUtil.throwConflictException("employee with this email already exists!");
        }
        final Employee employee = new Employee();
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

    public EmployeeDetailDTO get(final Long id) {
        return employeeRepository.findById(id)
            .map(employee -> {
                final EmployeeDetailDTO employeeDetailDTO = new EmployeeDetailDTO();
                employeeDetailDTO.setId(employee.getId());
                employeeDetailDTO.setFirstName(employee.getFirstName());
                employeeDetailDTO.setLastName(employee.getLastName());
                employeeDetailDTO.setPhoneNumber(employee.getPhoneNumber());
                employeeDetailDTO.setCompanyName(employee.getCompany() != null ? employee.getCompany().getName() : null);
                employeeDetailDTO.setBranchName(employee.getBranch() != null ? employee.getBranch().getName() : null);
                employeeDetailDTO.setPositionName(employee.getPosition() != null ? employee.getPosition().getName() : null);
                employeeDetailDTO.setDepartmentName(employee.getDepartment() != null ? employee.getDepartment().getName() : null);
                return employeeDetailDTO;
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("employee with this ID does not exist"));
    }

    public List<EmployeeListDTO> getList() {
        return employeeRepository.findAll()
            .stream().map(employee -> {
                final EmployeeListDTO employeeListDTO = new EmployeeListDTO();
                employeeListDTO.setId(employee.getId());
                employeeListDTO.setFirstName(employee.getFirstName());
                employeeListDTO.setLastName(employee.getLastName());
                employeeListDTO.setPositionName(employee.getPosition() != null ? employee.getPosition().getName() : null);
                return employeeListDTO;
            }).collect(Collectors.toList());
    }

    @Transactional
    public Long update(final Long id, final EmployeeDTO employeeDTO) {
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

    public void authenticationSuccessHandler(final LoginVM vm) {
        final Employee employee = employeeRepository.
            findByPhoneNumber(vm.getUsername()).orElseThrow(
                () -> new NotFoundException("user.not.found with id: " + vm.getUsername()));
        employee.setFailedAttempts(null);
        employee.setLockTime(null);
        employeeRepository.save(employee);
    }

    public void authenticationFailureHandler(LoginVM vm, Exception e) {
        if (e instanceof BadCredentialsException) {
            final Employee employee = employeeRepository
                .findByPhoneNumber(vm.getUsername())
                .orElseThrow(
                    () -> new NotFoundException("employee.not.found with id: " + vm.getUsername()));
            Integer failedAttempts = employee.getFailedAttempts() != null ? employee.getFailedAttempts() + 1 : 1;
            employee.setFailedAttempts(failedAttempts);
            LocalDateTime lockTime = Optional.ofNullable(employee.getLockTime()).orElse(LocalDateTime.MIN);
            if (employee.getFailedAttempts() % 3 == 0 && lockTime.isBefore(LocalDateTime.now())) {
                employee.setLockTime(LocalDateTime.now().plusMinutes(20));
            }
            employeeRepository.save(employee);
        }
    }

    @Transactional
    public void delete(final Long id) {
        employeeRepository.deleteById(id);
    }


    public ListResult<EmployeeDTO> filterNameAndSurname(final EmployeeFilterDTO filter) {
        Page<EmployeeDTO> page = employeeRepository.filterNameAndSurname(filter);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

    public ListResult<EmployeeDTO> filterByDepartmentName(final EmployeeFilterDTO filter) {
        Page<EmployeeDTO> page = employeeRepository.filterByDepartmentName(filter);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

    public ListResult<EmployeeDTO> filterByNameSurname(final EmployeeFilterDTO filter) {
        Page<EmployeeDTO> page = employeeRepository.filterByNameSurname(filter);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

    public ListResult<EmployeeDTO> filterEmployeeByPosition(final EmployeeFilterDTO filter) {
        Page<EmployeeDTO> page = employeeRepository.filterEmployeeByPosition(filter);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAllEmployees().stream().map(mapper::toDTO).toList();
    }

    public List<EmployeeDTO> getByName(final String name) {
        return employeeRepository.findByName(name).stream().map(mapper::toDTO).toList();
    }

    public List<?> findIdAndNameBySurname(final String surname) {
        return employeeRepository.findIdsAndNamesBySurname(surname);
    }

    public List<?> getEmployeeDTOByPhone(final String phone) {
        return employeeRepository.findEmployeeDTOByPhone(phone);
    }

    public Long countEmployeeByDepartment(final String departmentName) {
        TypedQuery<Long> query = entityManager.createQuery(
            "select count (e) from Employee e inner join Department d on e.id = d.id where d.name =:name", Long.class);
        query.setParameter("name", departmentName);
        return query.getSingleResult();
    }

    public ListResult<EmployeeDTO> filterEmployee(final EmployeeFilterDTO search) {
        Page<EmployeeDTO> page = employeeRepository.filterEmployee(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

    public ListResult<EmployeeDTO> filterBySpecification(final EmployeeFilterDTO search) {
        Page<EmployeeDTO> page = employeeRepository.filterBySpecification(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

    @Transactional
    public void dismissedEmployee(final Long id) {
        final Employee employee = employeeRepository.findById(id).orElseThrow(
            () -> ExceptionUtil.throwNotFoundException("employee with this id does not exist!"));
        employee.setStatus(GeneralStatus.BLOCK);
        employeeRepository.save(employee);
        tokenStoreRepository.deleteAll(tokenStoreRepository.findAllByEmployeeId(id));
    }

    @Transactional
    public void saveBlockList(final Long id) {
        final Employee employee = employeeRepository.findById(id).orElseThrow(
            () -> ExceptionUtil.throwNotFoundException("employee with this id does not exist!"));
        employee.setStatus(GeneralStatus.BLOCK);
        employeeRepository.save(employee);

        if (!blockListRepository.existsById(id)) {
            blockListRepository.save(new BlackList(id));
        }
    }


    @Transactional
    public void updateStatus(final Long id, final GeneralStatus status) {
        final Employee employee = employeeRepository.findById(id).orElseThrow(
            () -> ExceptionUtil.throwNotFoundException("employee with this id does not exist!"));
        employee.setStatus(status);
        employeeRepository.save(employee);
        if (GeneralStatus.BLOCK == status) {
            blockListRepository.save(new BlackList(id));
        } else if (GeneralStatus.ACTIVE == status) {
            blockListRepository.deleteById(id);
        }
    }

   /* @Transactional
    public void updateStatus(Long id, GeneralStatus status) {
        employeeRepository.findById(id)
                .ifPresentOrElse(employee -> {
                    employee.setStatus(status);
                    employeeRepository.save(employee);
                    if (GeneralStatus.BLOCK == status) {
                        blockListRepository.save(new BlackList(id));
                    } else if (GeneralStatus.ACTIVE == status) {
                        blockListRepository.deleteById(id);
                    }
                }, () -> ExceptionUtil.throwNotFoundException("employee with this id does not exist!"));

    }*/
}

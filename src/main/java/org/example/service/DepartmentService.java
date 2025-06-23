package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ListResult;
import org.example.dto.base.CommonDTO;
import org.example.dto.deparment.DepartmentDetailDTO;
import org.example.dto.deparment.DepartmentIdNameBranchIdDTO;
import org.example.dto.filter.DepartmentFilterDTO;
import org.example.entity.Department;
import org.example.exception.ExceptionUtil;
import org.example.repository.DepartmentRepository;
import org.example.service.custom.DepartmentCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentCustomRepository departmentCustomRepository;


    @Transactional
    public Long create(final CommonDTO departmentDTO) {
        if (departmentDTO.getName() == null || departmentDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("department name is required");
        }
        final Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setBranchId(departmentDTO.getId());
        return departmentRepository.save(department).getId();
    }

    public DepartmentDetailDTO get(final Long id) {
        return departmentRepository.findById(id)
            .map(department -> {
                final DepartmentDetailDTO detailDTO = new DepartmentDetailDTO();
                detailDTO.setId(department.getId());
                detailDTO.setName(department.getName());
                detailDTO.setBranchId(department.getBranchId());
                return detailDTO;
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("department not found"));
    }

    public List<CommonDTO> getList() {
        return departmentRepository.findAll()
            .stream().map(department -> {
                final CommonDTO commonDTO = new CommonDTO();
                commonDTO.setId(department.getId());
                commonDTO.setName(department.getName());
                return commonDTO;
            }).toList();
    }

    @Transactional
    public Long update(final Long id, final CommonDTO departmentDTO) {
        if (departmentDTO.getName() == null || departmentDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("department name is required");
        }
        departmentRepository.findById(id)
            .map(department -> {
                department.setName(departmentDTO.getName());
                if (departmentDTO.getId() != null) {
                    department.setBranchId(departmentDTO.getId());
                }
                departmentRepository.save(department);
                return department.getId();
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("department with this ID does not exist"));
        return departmentDTO.getId();
    }

    @Transactional
    public void delete(final Long id) {
        departmentRepository.deleteById(id);
    }

    public List<DepartmentIdNameBranchIdDTO> getDepartmentsIdName() {
        return departmentRepository.findAllDepartments();
    }

    public ListResult<CommonDTO> filterDepartment(final DepartmentFilterDTO search) {
        Page<CommonDTO> page = departmentCustomRepository.filterDepartment(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

    public ListResult<CommonDTO> filterDepartmentBySpecification(final DepartmentFilterDTO search) {
        Page<CommonDTO> page = departmentCustomRepository.filterDepartmentBySpecification(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }
}

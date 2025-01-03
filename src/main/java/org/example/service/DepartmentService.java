package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.base.CommonDTO;
import org.example.dto.deparment.DepartmentDTO;
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
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DepartmentService {

    DepartmentRepository departmentRepository;
    DepartmentCustomRepository departmentCustomRepository;


    @Transactional
    public Long create(DepartmentDTO departmentDTO) {
        if (departmentDTO.getName() == null || departmentDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("department name is required");
        }
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setBranchId(departmentDTO.getBranchId());
        return departmentRepository.save(department).getId();
    }

    public DepartmentDetailDTO get(Long id) {
        return departmentRepository.findById(id)
                .map(department -> {
                    DepartmentDetailDTO detailDTO = new DepartmentDetailDTO();
                    detailDTO.setId(department.getId());
                    detailDTO.setName(department.getName());
                    detailDTO.setBranchId(department.getBranchId());
                    return detailDTO;
                }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("department not found"));
    }

    public List<CommonDTO> getList() {
        return departmentRepository.findAll()
                .stream().map(department -> {
                    CommonDTO commonDTO = new CommonDTO();
                    commonDTO.setId(department.getId());
                    commonDTO.setName(department.getName());
                    return commonDTO;
                }).toList();
    }

    @Transactional
    public Long update(Long id, DepartmentDTO departmentDTO) {
        if (departmentDTO.getName() == null || departmentDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("department name is required");
        }
        departmentRepository.findById(id)
                .map(department -> {
                    department.setName(departmentDTO.getName());
                    if (departmentDTO.getBranchId() != null) {
                        department.setBranchId(departmentDTO.getBranchId());
                    }
                    departmentRepository.save(department);
                    return department.getId();
                }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("department with this ID does not exist"));
        return departmentDTO.getId();
    }

    @Transactional
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    public List<DepartmentIdNameBranchIdDTO> getDepartmentsIdName() {
        return departmentRepository.findAllDepartments();
    }

    public Page<DepartmentDTO> filterDepartment(DepartmentFilterDTO search) {
        return departmentCustomRepository.filterDepartment(search);
    }

    public Page<DepartmentDTO> filterDepartmentBySpecification(DepartmentFilterDTO search) {
        return departmentCustomRepository.filterDepartmentBySpecification(search);
    }
}

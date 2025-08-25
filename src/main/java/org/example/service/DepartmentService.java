package org.example.service;

import org.example.dto.ListResult;
import org.example.dto.base.CommonDTO;
import org.example.dto.deparment.DepartmentDetailDTO;
import org.example.dto.deparment.DepartmentIdNameBranchIdDTO;
import org.example.dto.filter.DepartmentFilterDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DepartmentService {

    @Transactional
    Long create(final CommonDTO departmentDTO);

    DepartmentDetailDTO get(final Long id);

    List<CommonDTO> getList();

    @Transactional
    Long update(final Long id, final CommonDTO departmentDTO);

    @Transactional
    void delete(final Long id);

    List<DepartmentIdNameBranchIdDTO> getDepartmentsIdName();

    ListResult<CommonDTO> filterDepartment(final DepartmentFilterDTO search);

    ListResult<CommonDTO> filterDepartmentBySpecification(final DepartmentFilterDTO search);

}

package org.example.repository;

import org.example.dto.base.CommonDTO;
import org.example.dto.filter.DepartmentFilterDTO;
import org.springframework.data.domain.Page;

public interface DepartmentRepositoryCustom {
    Page<CommonDTO> filterDepartment(DepartmentFilterDTO search);

    Page<CommonDTO> filterDepartmentBySpecification(DepartmentFilterDTO search);


}

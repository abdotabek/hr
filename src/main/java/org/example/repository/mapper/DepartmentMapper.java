package org.example.repository.mapper;


import org.example.dto.base.CommonDTO;
import org.example.entity.Department;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface DepartmentMapper extends BaseMapper<CommonDTO, Department> {

    CommonDTO toDTO(Department department);
}

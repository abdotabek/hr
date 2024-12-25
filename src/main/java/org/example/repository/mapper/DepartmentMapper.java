package org.example.repository.mapper;


import org.example.dto.deparment.DepartmentDTO;
import org.example.entity.Department;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface DepartmentMapper extends BaseMapper<DepartmentDTO, Department> {

    DepartmentDTO toDTO(Department department);
}

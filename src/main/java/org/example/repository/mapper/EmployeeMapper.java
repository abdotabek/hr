package org.example.repository.mapper;


import org.example.dto.employee.EmployeeDTO;
import org.example.entity.Employee;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface EmployeeMapper extends BaseMapper<EmployeeDTO, Employee> {


    EmployeeDTO toDTO(Employee employee);

}

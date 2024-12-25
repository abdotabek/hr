package org.example.dto.employee;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dto.enums.EmployeeRole;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDTO {
    Long id;

    String firstName;

    String lastName;

    String email;

    String phoneNumber;

    String password;

    Long companyId;

    Long branchId;

    Long departmentId;

    Long positionId;

    EmployeeRole role;


    /*ProfileRole role;*/

}

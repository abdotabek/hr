package org.example.dto.employee;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDetailDTO {
    Long id;

    String firstName;

    String lastName;

    String phoneNumber;

    String login;

    String companyName;

    String branchName;

    String departmentName;

    String positionName;


}

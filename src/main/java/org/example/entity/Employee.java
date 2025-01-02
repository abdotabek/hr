package org.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dto.enums.EmployeeRole;
import org.example.dto.enums.GeneralStatus;

@Getter
@Setter
@Entity
@Table(name = "employee")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "email")
    String email;

    @Column(name = "phone_number", unique = true)
    String phoneNumber;

    @Column(name = "password")
    String password;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    GeneralStatus status = GeneralStatus.ACTIVE;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    EmployeeRole role;

    @Column(name = "company_id")
    Long companyId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id", updatable = false, insertable = false)
    Company company;

    @Column(name = "branch_id")
    Long branchId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "branch_id", updatable = false, insertable = false)
    Branch branch;

    @Column(name = "department_id")
    Long departmentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id", updatable = false, insertable = false)
    Department department;

    @Column(name = "position_id")
    Long positionId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "position_id", updatable = false, insertable = false)
    Position position;

}

package org.example.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dto.enums.EmployeeRole;
import org.example.dto.enums.GeneralStatus;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "employee")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE employee SET deleted = true WHERE id=?")
public class Employee extends BaseEntity {

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

    @Column(name = "deleted")
    Boolean deleted = false;    //soft delete flag

    @Column(name = "lock_time")
    private LocalDateTime lockTime;

    @Column(name = "failed_attempts")
    private Integer failedAttempts = 0;

}

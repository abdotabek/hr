package org.example.repository;

import org.example.dto.deparment.DepartmentIdNameBranchIdDTO;
import org.example.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
    /*constructor query*/
    @Query("SELECT new org.example.dto.deparment.DepartmentIdNameBranchIdDTO(d.id,d.name,d.branchId) FROM Department d")
    List<DepartmentIdNameBranchIdDTO> findAllDepartments();

}

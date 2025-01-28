package org.example.repository;

import org.example.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("update Task t set t.deleted = true  where t.employeeId=:employeeId")
    void deleteAllByEmployeeId(@Param("employeeId") Long employeeId);
}

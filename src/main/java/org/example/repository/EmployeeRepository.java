package org.example.repository;

import org.example.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom, JpaSpecificationExecutor<Employee> {

    @Query(value = "SELECT * FROM employee", nativeQuery = true)
    List<Employee> findAllEmployees();

    @Query(value = "SELECT * FROM employee WHERE first_name = :firstName", nativeQuery = true)
    List<Employee> findByName(final String firstName);

    @Query(value = "SELECT id, name FROM employee WHERE last_name = :lastName", nativeQuery = true)
    List<Object[]> findIdsAndNamesBySurname(final String lastName);

    @Query(value = "SELECT id AS id, name AS employeeName FROM employee WHERE phone_number = :phoneNumber", nativeQuery = true)
    List<?> findEmployeeDTOByPhone(final String phoneNumber);

    boolean existsEmployeeByEmail(final String email);

    boolean existsEmployeeByPhoneNumber(final String phoneNumber);

    Optional<Employee> findByPhoneNumber(String phone);

}

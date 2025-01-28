package org.example.repository;

import org.example.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

    /*Hibernate Query Language*/
    @Query("FROM Company WHERE tin = :tin")
    Company findByTin(@Param("tin") String tin);
}

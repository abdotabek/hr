package org.example.repository;

import org.example.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long>, BranchRepositoryCustom, JpaSpecificationExecutor<Branch> {

    /* Derived Query */
    Long countAllByCompanyId(Long companyId);

    /* Java Persistence Query Language */
    @Query("select count (b.id) from Branch b where b.company.id=:companyId")
    Long countAllByCompanyIdTest(@Param("companyId") Long companyId);

    /* Java Persistence Query Language */
    @Query("select count (b.id) from Branch b where b.company.id=?1")
    Long countAllByCompanyIdTest2(Long companyId);

    /* Native SQL Query */
    @Query(value = "select count (b.id) from branch b inner join company c on b.company_id = c.id where c.id=?", nativeQuery = true)
    Long countAllByCompanyIdTest3(Long companyId);

    List<Branch> findByCompanyId(Long branchId);
}

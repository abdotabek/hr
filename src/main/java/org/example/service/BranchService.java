package org.example.service;

import org.example.dto.ListResult;
import org.example.dto.base.CommonDTO;
import org.example.dto.branch.BranchDTO;
import org.example.dto.branch.BranchDetailsDTO;
import org.example.dto.branch.BranchIdNameCompanyDTO;
import org.example.dto.filter.BranchFilterDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BranchService {

    @Transactional
    Long create(final BranchDTO branchDTO);

    BranchDetailsDTO get(final Long id);

    List<CommonDTO> getList();

    @Transactional
    Long update(final Long id, final BranchDTO branchDTO);

    @Transactional
    void delete(final Long id);

    List<BranchDTO> getAllBranches();

    List<BranchDTO> getBranchesByName(final String name);

    List<BranchDTO> getBranchesByCompanyId(final Long companyId);

    Long getBranchCountByCompanyId(final Long companyId);

    List<String> getAllBranchNames();

    List<BranchIdNameCompanyDTO> getBranchList();

    ListResult<BranchDTO> filterBranch(final BranchFilterDTO branchFilterDTO);

    ListResult<BranchDTO> filterBranchBySpecification(final BranchFilterDTO search);
}

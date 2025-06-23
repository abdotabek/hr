package org.example.repository.imp;

import org.example.dto.branch.BranchDTO;
import org.example.dto.filter.BranchFilterDTO;
import org.springframework.data.domain.Page;

public interface BranchRepositoryCustom {
    Page<BranchDTO> filterBranch(BranchFilterDTO search);

    Page<BranchDTO> filterBranchBySpecification(BranchFilterDTO search);
}

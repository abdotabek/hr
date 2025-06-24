package org.example.repository;

import org.example.dto.company.CompanyDTO;
import org.example.dto.filter.CompanyFilterDTO;
import org.springframework.data.domain.Page;

public interface CompanyRepositoryCustom {
    Page<CompanyDTO> filterCompany(CompanyFilterDTO search);

    Page<CompanyDTO> filterCompanyBySpecification(CompanyFilterDTO search);

}

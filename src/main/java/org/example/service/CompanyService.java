package org.example.service;

import org.example.dto.ListResult;
import org.example.dto.address.AddressDetailsDTO;
import org.example.dto.company.CompanyDTO;
import org.example.dto.company.CompanyDetailDTO;
import org.example.dto.company.CompanyListDTO;
import org.example.dto.filter.CompanyFilterDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CompanyService {
    @Transactional
    Long create(final CompanyDTO companyDTO);

    CompanyDTO getCompanyById(final Long id);

    CompanyDetailDTO get(final Long id);

    List<CompanyListDTO> getList();

    @Transactional
    Long update(final Long id, final CompanyDTO companyDTO);

    @Transactional
    void delete(final Long id);

    CompanyDTO getByTin(final String tin);

    ListResult<CompanyDTO> filterCompany(final CompanyFilterDTO companyFilterDTO);

    ListResult<CompanyDTO> filterCompanyBySpecification(final CompanyFilterDTO search);

    Long updateAddress(Long id, AddressDetailsDTO addressDTO);
}

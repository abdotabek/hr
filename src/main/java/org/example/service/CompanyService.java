package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ListResult;
import org.example.dto.address.AddressDetailsDTO;
import org.example.dto.base.CommonDTO;
import org.example.dto.company.CompanyDTO;
import org.example.dto.company.CompanyDetailDTO;
import org.example.dto.company.CompanyListDTO;
import org.example.dto.filter.CompanyFilterDTO;
import org.example.entity.Address;
import org.example.entity.Company;
import org.example.exception.ExceptionUtil;
import org.example.repository.CompanyRepository;
import org.example.repository.mapper.CompanyMapper;
import org.example.service.custom.CompanyCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper mapper;
    private final CompanyCustomRepository customRepository;

    @Transactional
    public Long create(final CompanyDTO companyDTO) {
        if (companyDTO.getName() == null || companyDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("Company name is required.");
        }
        final Company company = new Company();
        company.setName(companyDTO.getName());
        company.setTin(companyDTO.getTin());
        company.setBrand(companyDTO.getBrand());
        if (companyDTO.getAddressDTO() != null) {
            final Address address = new Address();
            address.setStreet(companyDTO.getAddressDTO().getStreet());
            address.setRegionId(companyDTO.getAddressDTO().getRegionId());
            address.setDistrictId(companyDTO.getAddressDTO().getDistrictId());
            company.setAddress(address);
        }
        return companyRepository.save(company).getId();
    }

    public synchronized CompanyDTO getCompanyById(final Long id) {
        return companyRepository.findById(id).map(mapper::toDTO)
            .orElseThrow(() -> ExceptionUtil.throwNotFoundException("company with this ID does not exist"));
    }

    public CompanyDetailDTO get(final Long id) {
        return companyRepository.findById(id)
            .map(company -> {
                final CompanyDetailDTO companyDetailDTO = new CompanyDetailDTO();
                companyDetailDTO.setId(company.getId());
                companyDetailDTO.setName(company.getName());
                companyDetailDTO.setTin(company.getTin());
                companyDetailDTO.setBrand(company.getBrand());
                if (company.getAddress() != null) {
                    final AddressDetailsDTO addressDetailsDTO = getAddressDetailsDTO(company);
                    companyDetailDTO.setAddressDetailsDTO(addressDetailsDTO);
                }
                return companyDetailDTO;
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("company with this ID does not exist"));
    }

    private static AddressDetailsDTO getAddressDetailsDTO(final Company company) {
        final AddressDetailsDTO addressDetailsDTO = new AddressDetailsDTO();
        final CommonDTO regionDTO = new CommonDTO();
        regionDTO.setId(company.getAddress().getRegionId());
        regionDTO.setName(company.getAddress().getRegion().getName());
        addressDetailsDTO.setRegion(String.valueOf(regionDTO));

        final CommonDTO districtDTO = new CommonDTO();
        districtDTO.setId(company.getAddress().getDistrictId());
        districtDTO.setName(company.getAddress().getDistrict().getName());
        addressDetailsDTO.setDistrict(String.valueOf(districtDTO));

        addressDetailsDTO.setStreet(company.getAddress().getStreet());
        return addressDetailsDTO;
    }

    public List<CompanyListDTO> getList() {
        return companyRepository.findAll()
            .stream()
            .map(company -> {
                final CompanyListDTO companyListDTO = new CompanyListDTO();
                companyListDTO.setId(company.getId());
                companyListDTO.setName(company.getName());
                companyListDTO.setTin(company.getTin());
                return companyListDTO;
            }).toList();
    }

    @Transactional
    public Long update(final Long id, final CompanyDTO companyDTO) {
        if (companyDTO.getName() == null || companyDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("company name is required");
        }
        return companyRepository.findById(id)
            .map(company -> {
                company.setName(companyDTO.getName());
                company.setTin(companyDTO.getTin());
                company.setBrand(companyDTO.getBrand());
                if (companyDTO.getAddressDTO() != null) {
                    final Address address = new Address();
                    address.setStreet(companyDTO.getAddressDTO().getStreet());
                    address.setRegionId(companyDTO.getAddressDTO().getRegionId());
                    address.setDistrictId(companyDTO.getAddressDTO().getDistrictId());
                    company.setAddress(address);
                }
                companyRepository.save(company);
                return companyDTO.getId();
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("company with this ID does not exist"));
    }

    @Transactional
    public void delete(final Long id) {
        companyRepository.deleteById(id);
    }

    public CompanyDTO getByTin(final String tin) {
        return mapper.toDTO(companyRepository.findByTin(tin));
    }

    public ListResult<CompanyDTO> filterCompany(final CompanyFilterDTO companyFilterDTO) {
        Page<CompanyDTO> page = customRepository.filterCompany(companyFilterDTO);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

    public ListResult<CompanyDTO> filterCompanyBySpecification(final CompanyFilterDTO search) {
        Page<CompanyDTO> page = customRepository.filterCompanyBySpecification(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

}

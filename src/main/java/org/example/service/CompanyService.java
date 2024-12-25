package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CompanyService {
    CompanyRepository companyRepository;
    CompanyMapper mapper;
    CompanyCustomRepository customRepository;

    @Transactional
    public Long create(CompanyDTO companyDTO) {
        if (companyDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("Company name is required.");
        }
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setTin(companyDTO.getTin());
        company.setBrand(companyDTO.getBrand());
        if (companyDTO.getAddressDTO() != null) {
            Address address = new Address();
            address.setStreet(companyDTO.getAddressDTO().getStreet());
            address.setRegionId(companyDTO.getAddressDTO().getRegionId());
            address.setDistrictId(companyDTO.getAddressDTO().getDistrictId());
            company.setAddress(address);
        }
        return companyRepository.save(company).getId();
    }

    public synchronized CompanyDTO getCompanyById(Long id) {
        return companyRepository.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> ExceptionUtil.throwNotFoundException("company with this ID does not exist"));
    }

    public CompanyDetailDTO get(Long id) {
        return companyRepository.findById(id)
                .map(company -> {
                    CompanyDetailDTO companyDetailDTO = new CompanyDetailDTO();
                    companyDetailDTO.setId(company.getId());
                    companyDetailDTO.setName(company.getName());
                    companyDetailDTO.setTin(company.getTin());
                    companyDetailDTO.setBrand(company.getBrand());
                    if (company.getAddress() != null) {
                        AddressDetailsDTO addressDetailsDTO = new AddressDetailsDTO();
                        CommonDTO regionDTO = new CommonDTO();
                        regionDTO.setId(company.getAddress().getRegionId());
                        regionDTO.setName(company.getAddress().getRegion().getName());
                        addressDetailsDTO.setRegion(String.valueOf(regionDTO));

                        CommonDTO districtDTO = new CommonDTO();
                        districtDTO.setId(company.getAddress().getDistrictId());
                        districtDTO.setName(company.getAddress().getDistrict().getName());
                        addressDetailsDTO.setDistrict(String.valueOf(districtDTO));

                        addressDetailsDTO.setStreet(company.getAddress().getStreet());
                        companyDetailDTO.setAddressDetailsDTO(addressDetailsDTO);
                    }
                    return companyDetailDTO;
                }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("company with this ID does not exist"));
    }

    public List<CompanyListDTO> getList() {
        return companyRepository.findAll()
                .stream()
                .map(company -> {
                    CompanyListDTO companyListDTO = new CompanyListDTO();
                    companyListDTO.setId(company.getId());
                    companyListDTO.setName(company.getName());
                    companyListDTO.setTin(company.getTin());
                    return companyListDTO;
                }).toList();
    }

    @Transactional
    public Long update(Long id, CompanyDTO companyDTO) {
        if (companyDTO.getName() == null || companyDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("company data is required");
        }
        return companyRepository.findById(id)
                .map(company -> {
                    company.setName(companyDTO.getName());
                    company.setTin(companyDTO.getTin());
                    company.setBrand(companyDTO.getBrand());
                    if (companyDTO.getAddressDTO() != null) {
                        Address address = new Address();
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
    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    public CompanyDTO getByTin(String tin) {
        return mapper.toDTO(companyRepository.findByTin(tin));
    }

    public Page<CompanyDTO> filterCompany(CompanyFilterDTO companyFilterDTO) {
        return customRepository.filterCompany(companyFilterDTO);
    }

    public Page<CompanyDTO> filterCompanyBySpecification(CompanyFilterDTO search) {
        return customRepository.filterCompanyBySpecification(search);
    }

}

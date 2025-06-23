package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.dto.ListResult;
import org.example.dto.address.AddressDetailsDTO;
import org.example.dto.base.CommonDTO;
import org.example.dto.branch.BranchDTO;
import org.example.dto.branch.BranchDetailsDTO;
import org.example.dto.branch.BranchIdNameCompanyDTO;
import org.example.dto.filter.BranchFilterDTO;
import org.example.entity.Address;
import org.example.entity.Branch;
import org.example.exception.ExceptionUtil;
import org.example.repository.BranchRepository;
import org.example.repository.mapper.BranchMapper;
import org.example.service.custom.BranchCustomRepositoryImp;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final EntityManager entityManager;
    private final BranchRepository branchRepository;
    private final BranchMapper mapper;
    private final BranchCustomRepositoryImp customRepository;

    @Transactional
    public Long create(final BranchDTO branchDTO) {
        final Branch branch = new Branch();
        branch.setName(branchDTO.getName());
        branch.setCompanyId(branchDTO.getCompanyId());

        final Address address = new Address();
        address.setStreet(branchDTO.getAddressDTO().getStreet());
        address.setRegionId(branchDTO.getAddressDTO().getRegionId());
        address.setDistrictId(branchDTO.getAddressDTO().getDistrictId());
        branch.setAddress(address);
        return branchRepository.save(branch).getId();
    }

    public BranchDetailsDTO get(final Long id) {
        return branchRepository.findById(id)
            .map(branch -> {
                final BranchDetailsDTO branchDetailsDTO = new BranchDetailsDTO();
                branchDetailsDTO.setId(branch.getId());
                branchDetailsDTO.setName(branch.getName());
                if (branch.getCompanyId() != null && branch.getAddress() != null) {

                    final AddressDetailsDTO addressDetailsDTO = new AddressDetailsDTO();
                    final CommonDTO regionDTO = new CommonDTO();
                    regionDTO.setId(branch.getCompanyId());
                    regionDTO.setName(branch.getAddress().getRegion().getName());
                    addressDetailsDTO.setRegion(String.valueOf(regionDTO));

                    final CommonDTO districtDTO = new CommonDTO();
                    districtDTO.setId(branch.getAddress().getDistrict().getId());
                    districtDTO.setName(branch.getAddress().getDistrict().getName());
                    addressDetailsDTO.setDistrict(String.valueOf(districtDTO));

                    addressDetailsDTO.setStreet(branch.getAddress().getStreet());
                    branchDetailsDTO.setAddressDetailsDTO(addressDetailsDTO);
                }
                return branchDetailsDTO;
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("branch with this ID does not exist"));
    }

    public List<CommonDTO> getList() {
        return branchRepository.findAll()
            .stream()
            .map(branch -> {
                final CommonDTO branchListDTO = new CommonDTO();
                branchListDTO.setId(branch.getId());
                branchListDTO.setName(branch.getName());
                return branchListDTO;
            }).toList();
    }

    @Transactional
    public Long update(final Long id, final BranchDTO branchDTO) {
        if (branchDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("branch name is required");
        }
        return branchRepository.findById(id)
            .map(branch -> {
                branch.setName(branchDTO.getName());
                branch.setCompanyId(branchDTO.getCompanyId());
                final Address address = new Address();
                address.setStreet(branchDTO.getAddressDTO().getStreet());
                address.setRegionId(branchDTO.getAddressDTO().getRegionId());
                address.setDistrictId(branchDTO.getAddressDTO().getDistrictId());
                branch.setAddress(address);
                branchRepository.save(branch);
                return branch.getId();
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("branch with id not exist"));
    }

    @Transactional
    public void delete(final Long id) {
        branchRepository.deleteById(id);
    }

    // Получение всех Branch
    public List<BranchDTO> getAllBranches() {
        TypedQuery<Branch> query = entityManager.createQuery("SELECT b FROM Branch b", Branch.class);
        return query.getResultList()
            .stream()
            .map(mapper::toDTO)
            .toList();
    }

    // Получение Branch по имени
    public List<BranchDTO> getBranchesByName(final String name) {
        TypedQuery<Branch> query = entityManager.createQuery("SELECT b FROM Branch b WHERE b.name = :name", Branch.class);
        query.setParameter("name", name);
        return query.getResultList()
            .stream()
            .map(mapper::toDTO)
            .toList();
    }

    // Получение Branch по ID компании
    public List<BranchDTO> getBranchesByCompanyId(final Long companyId) {
        TypedQuery<Branch> query = entityManager.createQuery("SELECT b FROM Branch b WHERE b.company.id = :companyId", Branch.class);
        query.setParameter("companyId", companyId);
        return query.getResultList().stream().map(mapper::toDTO).toList();
    }

    public Long getBranchCountByCompanyId(final Long companyId) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT count(b.id) FROM Branch b WHERE b.company.id = :companyId", Long.class);
        query.setParameter("companyId", companyId);
        return query.getSingleResult();
    }


    public List<String> getAllBranchNames() {
        TypedQuery<String> query = entityManager.createQuery("SELECT b.name FROM Branch b", String.class);
        return query.getResultList();
    }

    public List<BranchIdNameCompanyDTO> getBranchList() {
        TypedQuery<Object[]> query = entityManager
            .createQuery("select b.id, b.name as branchName, b.company.id as companyId from Branch b", Object[].class);
        List<Object[]> results = query.getResultList();
        return results.stream().map(result -> {
            Long id = (Long) result[0];
            String name = (String) result[1];
            Long companyId = (Long) result[2];

            final BranchIdNameCompanyDTO branchListDTO = new BranchIdNameCompanyDTO();
            branchListDTO.setId(id);
            branchListDTO.setName(name);
            branchListDTO.setCompanyId(companyId);
            return branchListDTO;
        }).collect(Collectors.toList());
    }

    public ListResult<BranchDTO> filterBranch(final BranchFilterDTO branchFilterDTO) {
        Page<BranchDTO> page = customRepository.filterBranch(branchFilterDTO);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

    public ListResult<BranchDTO> filterBranchBySpecification(final BranchFilterDTO search) {
        Page<BranchDTO> page = customRepository.filterBranchBySpecification(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }
}

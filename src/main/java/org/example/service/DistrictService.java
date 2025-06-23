package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ListResult;
import org.example.dto.base.CommonDTO;
import org.example.dto.district.DistrictDetailDTO;
import org.example.dto.filter.DistrictFilterDTO;
import org.example.entity.District;
import org.example.exception.ExceptionUtil;
import org.example.repository.DistrictRepository;
import org.example.service.custom.DistrictCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictCustomRepository districtCustomRepository;

    @Transactional
    public Long create(final CommonDTO districtDTO) {
        if (districtDTO.getName() == null || districtDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("district name is required");
        }
        final District district = new District();
        district.setName(districtDTO.getName());
        district.setRegionId(districtDTO.getId());
        return districtRepository.save(district).getId();
    }

    public DistrictDetailDTO get(final Long id) {
        return districtRepository.findById(id)
            .map(district -> {
                final DistrictDetailDTO districtDetailDTO = new DistrictDetailDTO();
                districtDetailDTO.setId(district.getId());
                districtDetailDTO.setName(district.getName());
                districtDetailDTO.setRegionName(district.getRegion().getName());
                return districtDetailDTO;
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("district not found"));
    }

    public List<CommonDTO> getList() {
        return districtRepository.findAll()
            .stream().map(district -> {
                final CommonDTO commonDTO = new CommonDTO();
                commonDTO.setId(district.getId());
                commonDTO.setName(district.getName());
                return commonDTO;
            }).toList();
    }

    @Transactional
    public Long update(final Long id, final CommonDTO districtDTO) {
        if (districtDTO.getName() == null || districtDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("district data is required");
        }
        return districtRepository.findById(id)
            .map(district -> {
                if (districtDTO.getId() != null) {
                    district.setRegionId(districtDTO.getId());
                }
                district.setName(districtDTO.getName());
                districtRepository.save(district);
                return district.getId();
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("district with id not exist"));
    }

    @Transactional
    public void delete(final Long id) {
        districtRepository.findById(id);
    }

    public ListResult<CommonDTO> filterDistrict(final DistrictFilterDTO search) {
        Page<CommonDTO> page = districtCustomRepository.filterDistrict(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

    public ListResult<CommonDTO> filterDistrictBySpecification(final DistrictFilterDTO search) {
        Page<CommonDTO> page = districtCustomRepository.filterDistrictBySpecification(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }
}

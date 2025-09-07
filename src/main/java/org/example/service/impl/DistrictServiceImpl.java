package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.ListResult;
import org.example.dto.base.CommonDTO;
import org.example.dto.district.DistrictDetailDTO;
import org.example.dto.filter.DistrictFilterDTO;
import org.example.entity.District;
import org.example.exception.ExceptionUtil;
import org.example.repository.DistrictRepository;
import org.example.service.DistrictService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    @Override
    public Long create(final CommonDTO districtDTO) {
        if (districtDTO.getName() == null || districtDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("district name is required");
        }
        final District district = new District();
        district.setName(districtDTO.getName());
        district.setRegionId(districtDTO.getId());
        return districtRepository.save(district).getId();
    }

    @Override
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

    @Override
    public List<CommonDTO> getList() {
        return districtRepository.findAll()
            .stream().map(district -> {
                final CommonDTO commonDTO = new CommonDTO();
                commonDTO.setId(district.getId());
                commonDTO.setName(district.getName());
                return commonDTO;
            }).toList();
    }

    @Override
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

    @Override
    public void delete(final Long id) {
        districtRepository.findById(id);
    }

    @Override
    public ListResult<CommonDTO> filterDistrict(final DistrictFilterDTO search) {
        Page<CommonDTO> page = districtRepository.filterDistrict(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public ListResult<CommonDTO> filterDistrictBySpecification(final DistrictFilterDTO search) {
        Page<CommonDTO> page = districtRepository.filterDistrictBySpecification(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }
}

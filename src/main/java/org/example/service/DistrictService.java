package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.base.CommonDTO;
import org.example.dto.district.DistrictDTO;
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
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DistrictService {

    DistrictRepository districtRepository;
    DistrictCustomRepository districtCustomRepository;


    @Transactional
    public Long create(DistrictDTO districtDTO) {
        if (districtDTO.getName() == null || districtDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("district name is required");
        }
        District district = new District();
        district.setName(districtDTO.getName());
        district.setRegionId(districtDTO.getRegionId());
        return districtRepository.save(district).getId();
    }

    public DistrictDetailDTO get(Long id) {
        return districtRepository.findById(id)
                .map(district -> {
                    DistrictDetailDTO districtDetailDTO = new DistrictDetailDTO();
                    districtDetailDTO.setId(district.getId());
                    districtDetailDTO.setName(district.getName());
                    districtDetailDTO.setRegionName(district.getRegion().getName());
                    return districtDetailDTO;
                }).orElseThrow(() ->
                        ExceptionUtil.throwNotFoundException("district not found"));
    }

    public List<CommonDTO> getList() {
        return districtRepository.findAll()
                .stream().map(district -> {
                    CommonDTO commonDTO = new CommonDTO();
                    commonDTO.setId(district.getId());
                    commonDTO.setName(district.getName());
                    return commonDTO;
                }).toList();
    }

    @Transactional
    public Long update(Long id, DistrictDTO districtDTO) {
        if (districtDTO.getName() == null || districtDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("district data is required");
        }
        return districtRepository.findById(id)
                .map(district -> {
                    if (districtDTO.getRegionId() != null) {
                        district.setRegionId(districtDTO.getRegionId());
                    }
                    district.setName(districtDTO.getName());
                    districtRepository.save(district);
                    return district.getId();
                }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("district with id not exist"));
    }

    @Transactional
    public void delete(Long id) {
        districtRepository.findById(id);
    }

    public Page<DistrictDTO> filterDistrict(DistrictFilterDTO search) {
        return districtCustomRepository.filterDistrict(search);
    }

    public Page<DistrictDTO> filterDistrictBySpecification(DistrictFilterDTO search) {
        return districtCustomRepository.filterDistrictBySpecification(search);
    }
}

package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.RegionFilterDTO;
import org.example.entity.Region;
import org.example.exception.ExceptionUtil;
import org.example.repository.RegionRepository;
import org.example.service.custom.RegionCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RegionService {
    RegionRepository regionRepository;
    RegionCustomRepository regionCustomRepository;


    @Transactional
    public Long create(CommonDTO regionDTO) {
        Region region = new Region();
        region.setName(regionDTO.getName());
        return regionRepository.save(region).getId();
    }

    public CommonDTO get(Long id) {
        return regionRepository.findById(id)
                .map(region -> {
                    CommonDTO regionDTO = new CommonDTO();
                    regionDTO.setId(region.getId());
                    regionDTO.setName(region.getName());
                    return regionDTO;
                }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("region with this ID does not exist"));
    }

    public List<CommonDTO> getList() {
        return regionRepository.findAll()
                .stream().map(region -> {
                    CommonDTO regionDTO = new CommonDTO();
                    regionDTO.setId(region.getId());
                    regionDTO.setName(region.getName());
                    return regionDTO;
                }).toList();
    }

    @Transactional
    public Long update(Long id, CommonDTO regionDTO) {
        if (regionDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("region data is required");
        }
        return regionRepository.findById(id)
                .map(region -> {
                    region.setName(regionDTO.getName());
                    return region.getId();
                }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("region with id not exist"));
    }

    @Transactional
    public void delete(Long id) {
        regionRepository.findById(id);
    }

    public Page<CommonDTO> filterRegion(RegionFilterDTO search) {
        return regionCustomRepository.filterRegion(search);
    }

    public Page<CommonDTO> filterRegionBySpecification(RegionFilterDTO search) {
        return regionCustomRepository.filterRegionBySpecification(search);
    }
}

package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.ListResult;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.RegionFilterDTO;
import org.example.entity.Region;
import org.example.exception.ExceptionUtil;
import org.example.repository.RegionRepository;
import org.example.service.RegionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public Long create(final CommonDTO regionDTO) {
        if (regionDTO.getName() == null || regionDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("region name is required!");
        }
        final Region region = new Region();
        region.setName(regionDTO.getName());
        return regionRepository.save(region).getId();
    }

    @Override
    public CommonDTO get(final Long id) {
        return regionRepository.findById(id)
            .map(region -> {
                final CommonDTO regionDTO = new CommonDTO();
                regionDTO.setId(region.getId());
                regionDTO.setName(region.getName());
                return regionDTO;
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("region with this ID does not exist"));
    }

    @Override
    public List<CommonDTO> getList() {
        return regionRepository.findAll()
            .stream().map(region -> {
                final CommonDTO regionDTO = new CommonDTO();
                regionDTO.setId(region.getId());
                regionDTO.setName(region.getName());
                return regionDTO;
            }).toList();
    }

    @Override
    public Long update(final Long id, final CommonDTO regionDTO) {
        if (regionDTO.getName() == null || regionDTO.getName().isEmpty()) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("region name is required");
        }
        return regionRepository.findById(id)
            .map(region -> {
                region.setName(regionDTO.getName());
                return region.getId();
            }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("region with id not exist"));
    }

    @Override
    public void delete(final Long id) {
        regionRepository.findById(id);
    }

    @Override
    public ListResult<CommonDTO> filterRegion(final RegionFilterDTO search) {
        Page<CommonDTO> page = regionRepository.filterRegion(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }

    @Override
    public ListResult<CommonDTO> filterRegionBySpecification(final RegionFilterDTO search) {
        Page<CommonDTO> page = regionRepository.filterRegionBySpecification(search);
        return new ListResult<>(page.getContent(), page.getTotalElements());
    }
}

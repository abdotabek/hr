package org.example.service.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.RegionFilterDTO;
import org.example.entity.Region;
import org.example.exception.ExceptionUtil;
import org.example.repository.RegionRepository;
import org.example.repository.imp.RegionRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegionCustomRepositoryImp implements RegionRepositoryCustom {
    private final EntityManager entityManager;
    private final RegionRepository regionRepository;

    @Override
    public Page<CommonDTO> filterRegion(RegionFilterDTO search) {
        StringBuilder select = new StringBuilder("select r");
        StringBuilder count = new StringBuilder("select count(r)");
        StringBuilder jpql = new StringBuilder(" from Region r where 1=1");
        if (!StringUtils.hasText(search.getSearch())) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("region data is required");
        }
        jpql.append(" and lower(r.name) like :search");

        select.append(jpql);
        count.append(jpql);

        TypedQuery<Region> query = entityManager.createQuery(select.toString(), Region.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(count.toString(), Long.class);

        if (StringUtils.hasText(search.getSearch())) {
            query.setParameter("search", "%" + search.getSearch().toLowerCase() + "%");
            countQuery.setParameter("search", "%" + search.getSearch().toLowerCase() + "%");
        }
        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);

        List<Region> regions = query.getResultList();
        Long totalElements = countQuery.getSingleResult();

        List<CommonDTO> commonDTOS = regions.stream().map(this::toDTO).toList();
        return new PageImpl<>(commonDTOS, PageRequest.of(pageNo, pageSize), totalElements);
    }

    @Override
    public Page<CommonDTO> filterRegionBySpecification(RegionFilterDTO search) {
        Specification<Region> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.hasText(search.getSearch())) {
                throw ExceptionUtil.throwCustomIllegalArgumentException("region data is required");
            }
            String searchPattern = "%" + search.getSearch().toLowerCase() + "%";
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern));
            if (search.getRegionId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), search.getRegionId()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

        Page<Region> regionsPage = regionRepository.findAll(specification, pageRequest);

        List<CommonDTO> commonDTOS = regionsPage.map(this::toDTO).stream().toList();
        return new PageImpl<>(commonDTOS, pageRequest, regionsPage.getTotalPages());

    }

    private CommonDTO toDTO(Region region) {
        CommonDTO regionDTO = new CommonDTO();
        regionDTO.setId(region.getId());
        regionDTO.setName(region.getName());
        return regionDTO;
    }
}

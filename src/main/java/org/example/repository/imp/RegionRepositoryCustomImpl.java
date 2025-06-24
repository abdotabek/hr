package org.example.repository.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.RegionFilterDTO;
import org.example.entity.Region;
import org.example.exception.ExceptionUtil;
import org.example.repository.RegionRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegionRepositoryCustomImpl implements RegionRepositoryCustom {
    private final EntityManager entityManager;

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
        if (!StringUtils.hasText(search.getSearch())) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("region data is required");
        }

        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

        String searchPattern = "%" + search.getSearch().toLowerCase() + "%";

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Основной запрос
        CriteriaQuery<Region> cq = cb.createQuery(Region.class);
        Root<Region> root = cq.from(Region.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(cb.lower(root.get("name")), searchPattern));
        if (search.getRegionId() != null) {
            predicates.add(cb.equal(root.get("id"), search.getRegionId()));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Region> typedQuery = entityManager.createQuery(cq)
            .setFirstResult(pageNo * pageSize)
            .setMaxResults(pageSize);

        List<Region> regions = typedQuery.getResultList();

        // Запрос для подсчёта total
        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        Root<Region> countRoot = countCq.from(Region.class);

        List<Predicate> countPredicates = new ArrayList<>();
        countPredicates.add(cb.like(cb.lower(countRoot.get("name")), searchPattern));
        if (search.getRegionId() != null) {
            countPredicates.add(cb.equal(countRoot.get("id"), search.getRegionId()));
        }

        countCq.select(cb.count(countRoot));
        countCq.where(cb.and(countPredicates.toArray(new Predicate[0])));

        Long total = entityManager.createQuery(countCq).getSingleResult();

        List<CommonDTO> commonDTOs = regions.stream().map(this::toDTO).toList();

        return new PageImpl<>(commonDTOs, pageRequest, total);
    }


    private CommonDTO toDTO(Region region) {
        CommonDTO regionDTO = new CommonDTO();
        regionDTO.setId(region.getId());
        regionDTO.setName(region.getName());
        return regionDTO;
    }
}

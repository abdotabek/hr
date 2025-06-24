package org.example.repository.imp;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.DistrictFilterDTO;
import org.example.entity.District;
import org.example.exception.ExceptionUtil;
import org.example.repository.DistrictRepositoryCustom;
import org.example.repository.mapper.DistrictMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DistrictRepositoryCustomImpl implements DistrictRepositoryCustom {
    private final EntityManager entityManager;
    private final DistrictMapper mapper;

    @Override
    public Page<CommonDTO> filterDistrict(final DistrictFilterDTO search) {
        StringBuilder select = new StringBuilder("select d");
        StringBuilder count = new StringBuilder("select count(d) ");
        StringBuilder jpql = new StringBuilder(" from District d where 1=1");

        if (!StringUtils.hasText(search.getSearch())) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("district data is required");
        }
        jpql.append(" and lower(d.name) like :search");
        select.append(jpql);
        count.append(jpql);

        TypedQuery<District> query = entityManager.createQuery(select.toString(), District.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(count.toString(), Long.class);
        if (StringUtils.hasText(search.getSearch())) {
            query.setParameter("search", "%" + search.getSearch().toLowerCase() + "%");
            countQuery.setParameter("search", "%" + search.getSearch().toLowerCase() + "%");
        }
        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);

        List<District> districts = query.getResultList();
        Long totalElement = countQuery.getSingleResult();

        List<CommonDTO> districtDTOS = districts.stream().map(mapper::toDTO).toList();
        return new PageImpl<>(districtDTOS, PageRequest.of(pageNo, pageSize), totalElement);
    }

    @Override
    public Page<CommonDTO> filterDistrictBySpecification(final DistrictFilterDTO search) {
        if (!StringUtils.hasText(search.getSearch())) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("district data is required");
        }

        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();

        String searchPattern = "%" + search.getSearch().toLowerCase() + "%";

        var cb = entityManager.getCriteriaBuilder();

        // Основной запрос для выборки
        var cq = cb.createQuery(District.class);
        var root = cq.from(District.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(cb.lower(root.get("name")), searchPattern));
        if (search.getDistrictId() != null) {
            predicates.add(cb.equal(root.get("id"), search.getDistrictId()));
        }
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<District> typedQuery = entityManager.createQuery(cq)
            .setFirstResult(pageNo * pageSize)
            .setMaxResults(pageSize);

        List<District> districts = typedQuery.getResultList();

        // Запрос для подсчёта
        var countCq = cb.createQuery(Long.class);
        var countRoot = countCq.from(District.class);

        List<Predicate> countPredicates = new ArrayList<>();
        countPredicates.add(cb.like(cb.lower(countRoot.get("name")), searchPattern));
        if (search.getDistrictId() != null) {
            countPredicates.add(cb.equal(countRoot.get("id"), search.getDistrictId()));
        }
        countCq.select(cb.count(countRoot));
        countCq.where(cb.and(countPredicates.toArray(new Predicate[0])));

        Long total = entityManager.createQuery(countCq).getSingleResult();

        List<CommonDTO> districtDTOs = districts.stream()
            .map(mapper::toDTO)
            .toList();

        return new PageImpl<>(districtDTOs, PageRequest.of(pageNo, pageSize), total);
    }

}

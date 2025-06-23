package org.example.service.custom;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.DistrictFilterDTO;
import org.example.entity.District;
import org.example.exception.ExceptionUtil;
import org.example.repository.DistrictRepository;
import org.example.repository.imp.DistrictRepositoryCustom;
import org.example.repository.mapper.DistrictMapper;
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
public class DistrictCustomRepositoryImp implements DistrictRepositoryCustom {
    private final EntityManager entityManager;
    private final DistrictMapper mapper;
    private final DistrictRepository districtRepository;

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
        Specification<District> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.hasText(search.getSearch())) {
                throw ExceptionUtil.throwCustomIllegalArgumentException("district data is required");
            }
            String searchPattern = "%" + search.getSearch().toLowerCase() + "%";
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern));
            if (search.getDistrictId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), search.getDistrictId()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

        Page<District> districts = districtRepository.findAll(specification, pageRequest);
        List<CommonDTO> districtDTOS = districts.stream().map(mapper::toDTO).toList();

        return new PageImpl<>(districtDTOS, pageRequest, districts.getTotalPages());

    }
}

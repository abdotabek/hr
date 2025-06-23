package org.example.service.custom;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.dto.company.CompanyDTO;
import org.example.dto.filter.CompanyFilterDTO;
import org.example.entity.Company;
import org.example.exception.ExceptionUtil;
import org.example.repository.CompanyRepository;
import org.example.repository.imp.CompanyRepositoryCustom;
import org.example.repository.mapper.CompanyMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyCustomRepositoryImp implements CompanyRepositoryCustom {
    private final CompanyMapper mapper;
    private final EntityManager entityManager;
    private final CompanyRepository companyRepository;

    @Override
    public Page<CompanyDTO> filterCompany(CompanyFilterDTO search) {
        StringBuilder select = new StringBuilder("select c");
        StringBuilder count = new StringBuilder("select count(c.id) ");
        StringBuilder jpql = new StringBuilder(" from Company c where 1=1");

        if (StringUtils.hasText(search.getSearch())) {
            jpql.append(" and (lower(c.name) like :search");
            jpql.append(" or lower(c.tin) like :search");
            jpql.append(" or lower(c.brand) like :search)");
        }

        select.append(jpql);
        count.append(jpql);

        TypedQuery<Company> query = entityManager.createQuery(select.toString(), Company.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(count.toString(), Long.class);

        if (StringUtils.hasText(search.getSearch())) {
            String searchParam = "%" + search.getSearch().toLowerCase() + "%";
            query.setParameter("search", searchParam);
            countQuery.setParameter("search", searchParam);
        }

        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);

        List<Company> companies = query.getResultList();
        Long totalElements = countQuery.getSingleResult();

        List<CompanyDTO> companyDTOS = companies.stream().map(mapper::toDTO).toList();
        return new PageImpl<>(companyDTOS, PageRequest.of(pageNo, pageSize), totalElements);
    }

    @Override
    public Page<CompanyDTO> filterCompanyBySpecification(CompanyFilterDTO search) {
        Specification<Company> specification = (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (!StringUtils.hasText(search.getSearch())) {
                throw ExceptionUtil.throwCustomIllegalArgumentException("data company is required");
            }
            String searchPattern = "%" + search.getSearch().toLowerCase() + "%";
            Predicate namePredicate = builder.like(builder.lower(root.get("name")), searchPattern);
            Predicate tinPredicate = builder.like(builder.lower(root.get("tin")), searchPattern);
            Predicate brandPredicate = builder.like(builder.lower(root.get("brand")), searchPattern);
            predicate = builder.and(predicate, builder.or(namePredicate, tinPredicate, brandPredicate));
            return predicate;
        };

        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();

        Page<Company> companyPage = companyRepository.findAll(specification, PageRequest.of(pageNo, pageSize));
        List<CompanyDTO> companyDTOS = companyPage.stream().map(mapper::toDTO).toList();

        return new PageImpl<>(companyDTOS, PageRequest.of(pageNo, pageSize), companyPage.getTotalElements());
    }
}

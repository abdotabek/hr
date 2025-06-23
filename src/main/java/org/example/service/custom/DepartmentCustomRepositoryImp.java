package org.example.service.custom;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.DepartmentFilterDTO;
import org.example.entity.Department;
import org.example.exception.ExceptionUtil;
import org.example.repository.DepartmentRepository;
import org.example.repository.imp.DepartmentRepositoryCustom;
import org.example.repository.mapper.DepartmentMapper;
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
public class DepartmentCustomRepositoryImp implements DepartmentRepositoryCustom {
    private final EntityManager entityManager;
    private final DepartmentMapper mapper;
    private final DepartmentRepository departmentRepository;

    @Override
    public Page<CommonDTO> filterDepartment(DepartmentFilterDTO search) {
        StringBuilder select = new StringBuilder("select d");
        StringBuilder count = new StringBuilder("select count(d.id) ");
        StringBuilder jpql = new StringBuilder(" from Department d where 1=1");

        if (!StringUtils.hasText(search.getSearch())) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("department data is required");
        }
        jpql.append(" and lower(d.name) like :search");
        select.append(jpql);
        count.append(jpql);

        TypedQuery<Department> query = entityManager.createQuery(select.toString(), Department.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(count.toString(), Long.class);
        if (StringUtils.hasText(search.getSearch())) {
            query.setParameter("search", "%" + search.getSearch().toLowerCase() + "%");
            countQuery.setParameter("search", "%" + search.getSearch().toLowerCase() + "%");
        }
        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);

        List<Department> departments = query.getResultList();
        Long totalElement = countQuery.getSingleResult();

        List<CommonDTO> departmentDTOS = departments.stream().map(mapper::toDTO).toList();
        return new PageImpl<>(departmentDTOS, PageRequest.of(pageNo, pageSize), totalElement);
    }

    @Override
    public Page<CommonDTO> filterDepartmentBySpecification(DepartmentFilterDTO search) {
        Specification<Department> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.hasText(search.getSearch())) {
                throw ExceptionUtil.throwCustomIllegalArgumentException("department data is required");
            }
            String searchPattern = "%" + search.getSearch().toLowerCase() + "%";
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern));
            if (search.getDepartmentId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), search.getDepartmentId()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

        Page<Department> departments = departmentRepository.findAll(specification, pageRequest);
        List<CommonDTO> departmentDTOS = departments.map(mapper::toDTO).toList();

        return new PageImpl<>(departmentDTOS, pageRequest, departments.getTotalPages());
    }
}

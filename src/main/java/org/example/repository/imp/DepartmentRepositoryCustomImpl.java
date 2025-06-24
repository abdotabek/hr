package org.example.repository.imp;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.DepartmentFilterDTO;
import org.example.entity.Department;
import org.example.exception.ExceptionUtil;
import org.example.repository.DepartmentRepositoryCustom;
import org.example.repository.mapper.DepartmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DepartmentRepositoryCustomImpl implements DepartmentRepositoryCustom {
    private final EntityManager entityManager;
    private final DepartmentMapper mapper;

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
        if (!StringUtils.hasText(search.getSearch())) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("department data is required");
        }

        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();

        String searchPattern = "%" + search.getSearch().toLowerCase() + "%";

        var cb = entityManager.getCriteriaBuilder();

        // Основной запрос для выборки
        var cq = cb.createQuery(Department.class);
        var root = cq.from(Department.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(cb.lower(root.get("name")), searchPattern));
        if (search.getDepartmentId() != null) {
            predicates.add(cb.equal(root.get("id"), search.getDepartmentId()));
        }
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Department> typedQuery = entityManager.createQuery(cq)
            .setFirstResult(pageNo * pageSize)
            .setMaxResults(pageSize);

        List<Department> departments = typedQuery.getResultList();

        // Запрос для подсчёта
        var countCq = cb.createQuery(Long.class);
        var countRoot = countCq.from(Department.class);

        List<Predicate> countPredicates = new ArrayList<>();
        countPredicates.add(cb.like(cb.lower(countRoot.get("name")), searchPattern));
        if (search.getDepartmentId() != null) {
            countPredicates.add(cb.equal(countRoot.get("id"), search.getDepartmentId()));
        }
        countCq.select(cb.count(countRoot));
        countCq.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = entityManager.createQuery(countCq).getSingleResult();

        List<CommonDTO> departmentDTOs = departments.stream()
            .map(mapper::toDTO)
            .toList();

        return new PageImpl<>(departmentDTOs, PageRequest.of(pageNo, pageSize), total);
    }


    /*@Override
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
    }*/
}

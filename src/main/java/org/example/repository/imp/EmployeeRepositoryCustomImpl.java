package org.example.repository.imp;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.filter.EmployeeFilterDTO;
import org.example.entity.Employee;
import org.example.entity.Position;
import org.example.exception.ExceptionUtil;
import org.example.repository.EmployeeRepositoryCustom;
import org.example.repository.mapper.EmployeeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {
    private final EntityManager entityManager;
    private final EmployeeMapper mapper;

    @Override
    public Page<EmployeeDTO> filterNameAndSurname(EmployeeFilterDTO filter) {
        StringBuilder select = new StringBuilder("select e");
        StringBuilder count = new StringBuilder("select count(e.id) ");
        StringBuilder jpql = new StringBuilder(" from Employee e where 1=1");
        Map<String, Object> params = new HashMap<>();

        if (filter.getEmployeeId() != null) {
            jpql.append(" and e.id = :employeeId");
            params.put("employeeId", filter.getEmployeeId());
        }

        if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {
            jpql.append(" and (lower(e.firstName) like :search or lower(e.lastName) like :search)");
            params.put("search", "%" + filter.getSearch().toLowerCase() + "%");
        }

        select.append(jpql);
        count.append(jpql);

        TypedQuery<Employee> query = entityManager.createQuery(select.toString(), Employee.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(count.toString(), Long.class);

        params.forEach((k, v) -> {
            query.setParameter(k, v);
            countQuery.setParameter(k, v);
        });

        int pageNo = filter.getPageNo();
        int pageSize = filter.getPageSize();
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);

        List<Employee> employees = query.getResultList();
        Long totalElements = countQuery.getSingleResult();
        List<EmployeeDTO> employeeDTOs = employees.stream().map(mapper::toDTO).collect(Collectors.toList());

        return new PageImpl<>(employeeDTOs, PageRequest.of(pageNo, pageSize), totalElements);
    }

    @Override
    public Page<EmployeeDTO> filterByDepartmentName(EmployeeFilterDTO filter) {
        StringBuilder select = new StringBuilder("select e");
        StringBuilder count = new StringBuilder("select count(e) ");
        StringBuilder jpql = new StringBuilder(" from Employee e inner join Department d on e.department.id = d.id where 1=1");

        Map<String, Object> params = new HashMap<>();

        if (filter.getEmployeeId() != null) {
            jpql.append(" and e.id = :employeeId");
            params.put("employeeId", filter.getEmployeeId());
        }

        if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {
            jpql.append(" and (lower(d.name) like :search or lower(e.firstName) like :search or lower(e.lastName) like :search)");
            params.put("search", "%" + filter.getSearch().toLowerCase() + "%");
        }

        select.append(jpql);
        count.append(jpql);

        TypedQuery<Employee> query = entityManager.createQuery(select.toString(), Employee.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(count.toString(), Long.class);

        params.forEach((k, v) -> {
            query.setParameter(k, v);
            countQuery.setParameter(k, v);
        });

        int pageNo = filter.getPageNo();
        int pageSize = filter.getPageSize();
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);

        List<Employee> employees = query.getResultList();
        Long totalElements = countQuery.getSingleResult();

        List<EmployeeDTO> employeeDTOs = employees.stream().map(mapper::toDTO).collect(Collectors.toList());

        return new PageImpl<>(employeeDTOs, PageRequest.of(pageNo, pageSize), totalElements);
    }

    @Override
    public Page<EmployeeDTO> filterByNameSurname(EmployeeFilterDTO filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.hasText(filter.getSearch())) {
            String pattern = "%" + filter.getSearch().toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(root.get("firstName")), pattern),
                cb.like(cb.lower(root.get("lastName")), pattern)
            ));
        }
        query.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Employee> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(filter.getPageNo() * filter.getPageSize());
        typedQuery.setMaxResults(filter.getPageSize());
        List<Employee> employees = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Employee> countRoot = countQuery.from(Employee.class);
        countQuery.select(cb.count(countRoot)).where(predicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(
            employees.stream().map(mapper::toDTO).toList(),
            PageRequest.of(filter.getPageNo(), filter.getPageSize()),
            total
        );
    }

    @Override
    public Page<EmployeeDTO> filterEmployeeByPosition(EmployeeFilterDTO filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        Join<Employee, Position> positionJoin = root.join("position", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.hasText(filter.getSearch())) {
            String pattern = "%" + filter.getSearch().toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(root.get("firstName")), pattern),
                cb.like(cb.lower(root.get("lastName")), pattern),
                cb.like(cb.lower(positionJoin.get("name")), pattern)
            ));
        }
        query.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Employee> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(filter.getPageNo() * filter.getPageSize());
        typedQuery.setMaxResults(filter.getPageSize());
        List<Employee> employees = typedQuery.getResultList();

        // Считаем общее количество
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Employee> countRoot = countQuery.from(Employee.class);
        Join<Employee, Position> countJoin = countRoot.join("position", JoinType.LEFT);
        countQuery.select(cb.count(countRoot)).where(predicates.toArray(new Predicate[0]));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(
            employees.stream().map(mapper::toDTO).toList(),
            PageRequest.of(filter.getPageNo(), filter.getPageSize()),
            total
        );
    }

    @Override
    public Page<EmployeeDTO> filterEmployee(EmployeeFilterDTO search) {
        StringBuilder select = new StringBuilder("select e");
        StringBuilder count = new StringBuilder("select count(e)");
        StringBuilder jpql = new StringBuilder(" from Employee e where 1=1");
        if (!StringUtils.hasText(search.getSearch())) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("employee data is required");
        }
        jpql.append(" and (lower(e.firstName) like :search");
        jpql.append(" or lower(e.lastName) like :search");
        jpql.append(" or lower(e.phoneNumber) like :search)");
        select.append(jpql);
        count.append(jpql);

        TypedQuery<Employee> query = entityManager.createQuery(select.toString(), Employee.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(count.toString(), Long.class);

        if (StringUtils.hasText(search.getSearch())) {
            query.setParameter("search", "%" + search.getSearch().toLowerCase() + "%");
            countQuery.setParameter("search", "%" + search.getSearch().toLowerCase() + "%");
        }
        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);

        List<Employee> employees = query.getResultList();
        Long totalElement = countQuery.getSingleResult();

        List<EmployeeDTO> employeeDTOS = employees.stream().map(mapper::toDTO).toList();
        return new PageImpl<>(employeeDTOS, PageRequest.of(pageNo, pageSize), totalElement);
    }

    @Override
    public Page<EmployeeDTO> filterBySpecification(EmployeeFilterDTO search) {
        if (!StringUtils.hasText(search.getSearch())) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("employee data is required");
        }

        String pattern = "%" + search.getSearch().toLowerCase() + "%";
        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();

        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(Employee.class);
        var root = cq.from(Employee.class);

        var predicate = cb.or(
            cb.like(cb.lower(root.get("firstName")), pattern),
            cb.like(cb.lower(root.get("lastName")), pattern),
            cb.like(cb.lower(root.get("email")), pattern),
            cb.like(cb.lower(root.get("phoneNumber")), pattern),
            cb.like(cb.lower(root.get("login")), pattern)
        );

        cq.where(predicate);

        var typedQuery = entityManager.createQuery(cq)
            .setFirstResult(pageNo * pageSize)
            .setMaxResults(pageSize);

        List<Employee> employees = typedQuery.getResultList();

        var countCq = cb.createQuery(Long.class);
        var countRoot = countCq.from(Employee.class);
        countCq.select(cb.count(countRoot)).where(
            cb.or(
                cb.like(cb.lower(countRoot.get("firstName")), pattern),
                cb.like(cb.lower(countRoot.get("lastName")), pattern),
                cb.like(cb.lower(countRoot.get("email")), pattern),
                cb.like(cb.lower(countRoot.get("phoneNumber")), pattern),
                cb.like(cb.lower(countRoot.get("login")), pattern)
            )
        );

        Long total = entityManager.createQuery(countCq).getSingleResult();

        List<EmployeeDTO> dtos = employees.stream().map(mapper::toDTO).toList();

        return new PageImpl<>(dtos, PageRequest.of(pageNo, pageSize), total);
    }
}

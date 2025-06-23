package org.example.service.custom;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.filter.EmployeeFilterDTO;
import org.example.entity.Employee;
import org.example.entity.Position;
import org.example.exception.ExceptionUtil;
import org.example.repository.EmployeeRepository;
import org.example.repository.imp.EmployeeRepositoryCustom;
import org.example.repository.mapper.EmployeeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EmployeeCustomRepositoryImp implements EmployeeRepositoryCustom {
    private final EntityManager entityManager;
    private final EmployeeRepository employeeRepository;
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
        Specification<Employee> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {
                String searchTerm = "%" + filter.getSearch().toLowerCase() + "%";

                Predicate firstName = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), searchTerm);
                Predicate lastName = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), searchTerm);
                predicates.add(criteriaBuilder.or(firstName, lastName));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize());
        return employeeRepository.findAll(spec, pageable).map(mapper::toDTO);
    }

    @Override
    public Page<EmployeeDTO> filterEmployeeByPosition(EmployeeFilterDTO filter) {
        Specification<Employee> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getSearch() != null && !filter.getSearch().isEmpty()) {

                Join<Employee, Position> positionJoin = root.join("position", JoinType.LEFT);
                String searchTerm = "%" + filter.getSearch().toLowerCase() + "%";

                Predicate byPositionName = criteriaBuilder.like(criteriaBuilder.lower(positionJoin.get("name")), searchTerm);
                Predicate firstName = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), searchTerm);
                Predicate lastName = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), searchTerm);

                predicates.add(criteriaBuilder.or(firstName, lastName, byPositionName));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageRequest.of(filter.getPageNo(), filter.getPageSize());
        return employeeRepository.findAll(specification, pageable).map(mapper::toDTO);
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
        Specification<Employee> specification = (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (!StringUtils.hasText(search.getSearch())) {
                throw ExceptionUtil.throwCustomIllegalArgumentException("employee data is required");
            }
            String searchPatter = "%" + search.getSearch().toLowerCase() + "%";
            Predicate firstPredicate = builder.like(builder.lower(root.get("firstName")), searchPatter);
            Predicate lastNamePredicate = builder.like(builder.lower(root.get("lastName")), searchPatter);
            Predicate emailPredicate = builder.like(builder.lower(root.get("email")), searchPatter);
            Predicate phonePredicate = builder.like(builder.lower(root.get("phoneNumber")), searchPatter);
            Predicate logonPredicate = builder.like(builder.lower(root.get("login")), searchPatter);
            predicate = builder.and(predicate, builder.or(firstPredicate, lastNamePredicate, emailPredicate, phonePredicate, logonPredicate));
            return predicate;
        };
        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();

        Page<Employee> employeePage = employeeRepository.findAll(specification, PageRequest.of(pageNo, pageSize));
        List<EmployeeDTO> employeeDTOS = employeePage.stream().map(mapper::toDTO).toList();

        return new PageImpl<>(employeeDTOS, PageRequest.of(pageNo, pageSize), employeePage.getTotalPages());
    }
}

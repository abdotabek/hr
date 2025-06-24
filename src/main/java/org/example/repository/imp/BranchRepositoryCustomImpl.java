package org.example.repository.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.dto.branch.BranchDTO;
import org.example.dto.filter.BranchFilterDTO;
import org.example.entity.Branch;
import org.example.exception.ExceptionUtil;
import org.example.repository.BranchRepositoryCustom;
import org.example.repository.mapper.BranchMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BranchRepositoryCustomImpl implements BranchRepositoryCustom {
    private final EntityManager entityManager;
    private final BranchMapper mapper;

    @Override
    public Page<BranchDTO> filterBranch(BranchFilterDTO search) {
        StringBuilder select = new StringBuilder("select b");
        StringBuilder count = new StringBuilder("select count(b) ");
        StringBuilder jpql = new StringBuilder(" from Branch b where 1=1");

        if (!StringUtils.hasText(search.getSearch())) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("branch data is required");
        }
        jpql.append(" and lower(b.name) like :search");
        select.append(jpql);
        count.append(jpql);

        TypedQuery<Branch> query = entityManager.createQuery(select.toString(), Branch.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(count.toString(), Long.class);

        if (StringUtils.hasText(search.getSearch())) {
            query.setParameter("search", "%" + search.getSearch().toLowerCase() + "%");
            countQuery.setParameter("search", "%" + search.getSearch().toLowerCase() + "%");
        }

        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();

        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);

        List<Branch> branches = query.getResultList();
        Long totalElement = countQuery.getSingleResult();

        List<BranchDTO> branchDTOS = branches.stream().map(mapper::toDTO).toList();
        return new PageImpl<>(branchDTOS, PageRequest.of(pageNo, pageSize), totalElement);
    }

    @Override
    public Page<BranchDTO> filterBranchBySpecification(BranchFilterDTO search) {
        if (!StringUtils.hasText(search.getSearch())) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("branch data is required");
        }

        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();
        String pattern = "%" + search.getSearch().toLowerCase() + "%";

        var cb = entityManager.getCriteriaBuilder();

        var cq = cb.createQuery(Branch.class);
        var root = cq.from(Branch.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(cb.lower(root.get("name")), pattern));
        if (search.getBranchId() != null) {
            predicates.add(cb.equal(root.get("id"), search.getBranchId()));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Branch> typedQuery = entityManager.createQuery(cq)
            .setFirstResult(pageNo * pageSize)
            .setMaxResults(pageSize);

        List<Branch> branchList = typedQuery.getResultList();

        var countCq = cb.createQuery(Long.class);
        var countRoot = countCq.from(Branch.class);

        List<Predicate> countPredicates = new ArrayList<>();
        countPredicates.add(cb.like(cb.lower(countRoot.get("name")), pattern));
        if (search.getBranchId() != null) {
            countPredicates.add(cb.equal(countRoot.get("id"), search.getBranchId()));
        }

        countCq.select(cb.count(countRoot));
        countCq.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = entityManager.createQuery(countCq).getSingleResult();

        List<BranchDTO> branchDTOS = branchList.stream()
            .map(mapper::toDTO)
            .toList();

        return new PageImpl<>(branchDTOS, PageRequest.of(pageNo, pageSize), total);
    }


    /*@Override
    public Page<BranchDTO> filterBranchBySpecification(BranchFilterDTO search) {
        Specification<Branch> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.hasText(search.getSearch())) {
                throw ExceptionUtil.throwCustomIllegalArgumentException("branch data is required");
            }
            String searchPattern = "%" + search.getSearch().toLowerCase() + "%";
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern));
            if (search.getBranchId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), search.getBranchId()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

        Page<Branch> branches = branchRepository.findAll(specification, pageRequest);
        List<BranchDTO> branchDTOS = branches.map(mapper::toDTO).toList();

        return new PageImpl<>(branchDTOS, pageRequest, branches.getTotalPages());
    }*/
}

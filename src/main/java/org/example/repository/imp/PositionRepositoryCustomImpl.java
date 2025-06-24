package org.example.repository.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.dto.base.CommonDTO;
import org.example.dto.filter.PositionFilterDTO;
import org.example.entity.Position;
import org.example.exception.ExceptionUtil;
import org.example.repository.PositionRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PositionRepositoryCustomImpl implements PositionRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public Page<CommonDTO> filterPosition(PositionFilterDTO search) {
        StringBuilder select = new StringBuilder("select p");
        StringBuilder count = new StringBuilder("select count(p) ");
        StringBuilder jpql = new StringBuilder(" from Position p where 1=1");
        if (!StringUtils.hasText(search.getSearch())) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("position data is required");
        }
        jpql.append(" and lower(p.name) like :search");

        select.append(jpql);
        count.append(jpql);

        TypedQuery<Position> query = entityManager.createQuery(select.toString(), Position.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(count.toString(), Long.class);
        if (StringUtils.hasText(search.getSearch())) {
            query.setParameter("search", "%" + search.getSearch().toLowerCase() + "%");
            countQuery.setParameter("search", "%" + search.getSearch().toLowerCase() + "%");
        }
        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);

        List<Position> positions = query.getResultList();
        Long totalElement = countQuery.getSingleResult();

        List<CommonDTO> positionDTS = positions.stream().map(this::toDTO).toList();
        return new PageImpl<>(positionDTS, PageRequest.of(pageNo, pageSize), totalElement);
    }

    /* @Override
     public Page<CommonDTO> filterPositionBySpecification(PositionFilterDTO search) {
         Specification<Position> specification = (root, query, criteriaBuilder) -> {
             List<Predicate> predicates = new ArrayList<>();
             if (!StringUtils.hasText(search.getSearch())) {
                 throw ExceptionUtil.throwCustomIllegalArgumentException("position data is required");
             }
             String searchPattern = "%" + search.getSearch().toLowerCase() + "%";
             predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern));

             if (search.getPositionId() != null) {
                 predicates.add(criteriaBuilder.equal(root.get("id"), search.getPositionId()));
             }
             return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
         };
         int pageNo = search.getPageNo();
         int pageSize = search.getPageSize();

         PageRequest pageRequest = PageRequest.of(pageNo, pageSize);

         Page<Position> positions = positionRepository.findAll(specification, pageRequest);
         List<CommonDTO> commonDTOS = positions.stream().map(this::toDTO).toList();

         return new PageImpl<>(commonDTOS, pageRequest, positions.getTotalPages());
     }*/
    @Override
    public Page<CommonDTO> filterPositionBySpecification(PositionFilterDTO search) {
        if (!StringUtils.hasText(search.getSearch())) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("position data is required");
        }

        int pageNo = search.getPageNo();
        int pageSize = search.getPageSize();

        String searchPattern = "%" + search.getSearch().toLowerCase() + "%";

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Основной запрос
        CriteriaQuery<Position> cq = cb.createQuery(Position.class);
        Root<Position> root = cq.from(Position.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(cb.lower(root.get("name")), searchPattern));
        if (search.getPositionId() != null) {
            predicates.add(cb.equal(root.get("id"), search.getPositionId()));
        }
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Position> typedQuery = entityManager.createQuery(cq)
            .setFirstResult(pageNo * pageSize)
            .setMaxResults(pageSize);

        List<Position> positions = typedQuery.getResultList();

        // Запрос для подсчёта
        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        Root<Position> countRoot = countCq.from(Position.class);

        List<Predicate> countPredicates = new ArrayList<>();
        countPredicates.add(cb.like(cb.lower(countRoot.get("name")), searchPattern));
        if (search.getPositionId() != null) {
            countPredicates.add(cb.equal(countRoot.get("id"), search.getPositionId()));
        }
        countCq.select(cb.count(countRoot));
        countCq.where(cb.and(countPredicates.toArray(new Predicate[0])));

        Long total = entityManager.createQuery(countCq).getSingleResult();

        List<CommonDTO> positionDTOs = positions.stream().map(this::toDTO).toList();

        return new PageImpl<>(positionDTOs, PageRequest.of(pageNo, pageSize), total);
    }


    private CommonDTO toDTO(Position position) {
        CommonDTO positionDTO = new CommonDTO();
        positionDTO.setId(position.getId());
        positionDTO.setName(position.getName());
        return positionDTO;
    }
}

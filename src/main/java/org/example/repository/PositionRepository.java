package org.example.repository;

import org.example.entity.Position;
import org.example.repository.imp.PositionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long>, PositionRepositoryCustom, JpaSpecificationExecutor<Position> {
}

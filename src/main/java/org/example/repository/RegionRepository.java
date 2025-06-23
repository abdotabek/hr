package org.example.repository;

import org.example.entity.Region;
import org.example.repository.imp.RegionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>, RegionRepositoryCustom, JpaSpecificationExecutor<Region> {
}
